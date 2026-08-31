/* ============================================================
   calendario.js — todo lo que se dibuja en pantalla.
   Recibe datos ya filtrados desde app.js y devuelve HTML.
   ============================================================ */

/* ---------- utilidades de fecha ---------- */
const Fechas = {
  DIAS: ["lun", "mar", "mié", "jue", "vie", "sáb", "dom"],
  DIAS_LARGO: ["lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"],
  MESES: ["enero", "febrero", "marzo", "abril", "mayo", "junio",
          "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"],

  hoy() {
    const d = new Date();
    return new Date(d.getFullYear(), d.getMonth(), d.getDate());
  },

  /* "2026-08-31T23:00" -> Date local.
     Se parte a mano en vez de usar new Date(texto) porque ese
     constructor interpreta algunas cadenas como UTC y la fecha
     se recorre un día. */
  aFecha(texto) {
    if (!texto) return null;
    const m = String(texto).match(/^(\d{4})-(\d{2})-(\d{2})(?:[T ](\d{2}):(\d{2}))?/);
    if (!m) return null;
    return new Date(+m[1], +m[2] - 1, +m[3], m[4] ? +m[4] : 0, m[5] ? +m[5] : 0);
  },

  clave(d) {
    if (!d) return "";
    const p = function (n) { return String(n).padStart(2, "0"); };
    return d.getFullYear() + "-" + p(d.getMonth() + 1) + "-" + p(d.getDate());
  },

  mismoDia(a, b) {
    return a && b && a.getFullYear() === b.getFullYear()
      && a.getMonth() === b.getMonth() && a.getDate() === b.getDate();
  },

  sumarDias(d, n) {
    const x = new Date(d.getTime());
    x.setDate(x.getDate() + n);
    return x;
  },

  /* Índice de día empezando en lunes: lunes 0 … domingo 6 */
  indiceDia(d) { return (d.getDay() + 6) % 7; },

  inicioSemana(d) { return Fechas.sumarDias(d, -Fechas.indiceDia(d)); },

  hora(d) {
    if (!d) return "";
    const p = function (n) { return String(n).padStart(2, "0"); };
    return p(d.getHours()) + ":" + p(d.getMinutes());
  },

  /* "en 3 días", "hoy", "hace 2 días" */
  relativo(d) {
    if (!d) return "sin fecha";
    const dias = Math.round((new Date(d.getFullYear(), d.getMonth(), d.getDate()) - Fechas.hoy()) / 86400000);
    if (dias === 0) return "hoy";
    if (dias === 1) return "mañana";
    if (dias === -1) return "ayer";
    if (dias > 1) return "en " + dias + " días";
    return "hace " + Math.abs(dias) + " días";
  },

  larga(d) {
    if (!d) return "Sin fecha";
    return Fechas.DIAS_LARGO[Fechas.indiceDia(d)] + " " + d.getDate() +
           " de " + Fechas.MESES[d.getMonth()] + " de " + d.getFullYear();
  }
};

/* ---------- helpers de presentación ---------- */
function esc(s) {
  return String(s == null ? "" : s)
    .replace(/&/g, "&amp;").replace(/</g, "&lt;")
    .replace(/>/g, "&gt;").replace(/"/g, "&quot;");
}

function cursoDe(clave) {
  for (let i = 0; i < CURSOS.length; i++) if (CURSOS[i].clave === clave) return CURSOS[i];
  return { clave: clave, nombre: clave, color: "#64748b", unidades: [], reglas: [] };
}

const Pintar = {

  /* Una tarea como chip pequeño dentro de una celda del calendario */
  chip(t) {
    const c = cursoDe(t.curso);
    const clases = ["chip"];
    if (t.vencida) clases.push("chip--vencida");
    if (t.estadoActual === "entregada" || t.estadoActual === "confirmada") clases.push("chip--lista");
    return '<button class="' + clases.join(" ") + '" data-tarea="' + esc(t.id) + '"' +
           ' style="--c:' + esc(c.color) + '"' +
           ' title="' + esc(c.nombre + " · " + t.titulo) + '">' +
           '<span class="chip__hora">' + (t.fechaObj ? esc(Fechas.hora(t.fechaObj)) : "") + '</span>' +
           '<span class="chip__txt">' + esc(t.titulo) + '</span>' +
           '</button>';
  },

  /* Una tarea como renglón de lista (vistas de lista, materias, archivo) */
  renglon(t) {
    const c = cursoDe(t.curso);
    const est = ESTADOS[t.estadoActual] || ESTADOS.pendiente;
    return '<button class="renglon' + (t.vencida ? " renglon--vencida" : "") + '" data-tarea="' + esc(t.id) + '"' +
             ' style="--c:' + esc(c.color) + '">' +
             '<span class="renglon__curso">' + esc(c.clave) + '</span>' +
             '<span class="renglon__cuerpo">' +
               '<span class="renglon__titulo">' + esc(t.titulo) + '</span>' +
               '<span class="renglon__meta">' + esc(TIPOS[t.tipo] || t.tipo) +
                 (t.fechaObj
                   ? ' · ' + esc(t.fechaObj.getDate() + " " + Fechas.MESES[t.fechaObj.getMonth()].slice(0, 3) +
                       " " + Fechas.hora(t.fechaObj)) +
                     ' · <span class="rel' + (t.vencida ? " rel--mal" : "") + '">' + esc(Fechas.relativo(t.fechaObj)) + '</span>' +
                     (t.fechaPorConfirmar ? ' <span class="duda" title="Fecha supuesta, confírmala con el profe">fecha por confirmar</span>' : '')
                   : ' · sin fecha') +
               '</span>' +
             '</span>' +
             '<span class="pastilla" style="--e:' + esc(est.color) + '">' + esc(est.etiqueta) + '</span>' +
           '</button>';
  },

  /* ---------- VISTA MES ---------- */
  mes(estado, tareas) {
    const primero = new Date(estado.anio, estado.mes, 1);
    const arranque = Fechas.inicioSemana(primero);
    const hoy = Fechas.hoy();

    // Índice: clave de día -> tareas de ese día
    const porDia = {};
    tareas.forEach(function (t) {
      if (!t.fechaObj) return;
      const k = Fechas.clave(t.fechaObj);
      (porDia[k] = porDia[k] || []).push(t);
    });

    let html = '<div class="mes">';
    html += '<div class="mes__cabecera">' +
      Fechas.DIAS.map(function (d) { return '<div class="mes__dia-nombre">' + d + '</div>'; }).join("") +
      '</div><div class="mes__rejilla">';

    for (let i = 0; i < 42; i++) {
      const d = Fechas.sumarDias(arranque, i);
      const k = Fechas.clave(d);
      const delMes = d.getMonth() === estado.mes;
      const finde = Fechas.indiceDia(d) >= 5;
      const lista = (porDia[k] || []).sort(function (a, b) { return a.fechaObj - b.fechaObj; });

      const clases = ["dia"];
      if (!delMes) clases.push("dia--fuera");
      if (finde) clases.push("dia--finde");
      if (Fechas.mismoDia(d, hoy)) clases.push("dia--hoy");
      if (estado.diaSel === k) clases.push("dia--sel");
      if (lista.length) clases.push("dia--con-tareas");

      const clasesH = HORARIO.filter(function (h) { return h.dia === Fechas.indiceDia(d) + 1; });

      html += '<div class="' + clases.join(" ") + '" data-fecha="' + k + '">' +
                '<div class="dia__alto">' +
                  '<span class="dia__num">' + d.getDate() + '</span>' +
                  (clasesH.length
                    ? '<span class="dia__clases">' + clasesH.map(function (h) {
                        return '<i style="background:' + esc(cursoDe(h.curso).color) + '" title="' +
                               esc(cursoDe(h.curso).nombre + " " + h.inicio) + '"></i>';
                      }).join("") + '</span>'
                    : '') +
                '</div>' +
                '<div class="dia__chips">' +
                  lista.slice(0, 3).map(Pintar.chip).join("") +
                  (lista.length > 3 ? '<span class="dia__mas">+' + (lista.length - 3) + ' más</span>' : '') +
                '</div>' +
              '</div>';
    }

    html += '</div></div>';
    return html;
  },

  /* ---------- VISTA SEMANA ---------- */
  semana(estado, tareas) {
    const base = estado.diaSel ? Fechas.aFecha(estado.diaSel) : Fechas.hoy();
    const arranque = Fechas.inicioSemana(base);
    const hoy = Fechas.hoy();

    let html = '<div class="semana">';
    for (let i = 0; i < 7; i++) {
      const d = Fechas.sumarDias(arranque, i);
      const k = Fechas.clave(d);
      const lista = tareas.filter(function (t) { return t.fechaObj && Fechas.clave(t.fechaObj) === k; })
                          .sort(function (a, b) { return a.fechaObj - b.fechaObj; });
      html += '<div class="semana__col' + (Fechas.mismoDia(d, hoy) ? " semana__col--hoy" : "") + '" data-fecha="' + k + '">' +
                '<div class="semana__cab">' +
                  '<span class="semana__dia">' + Fechas.DIAS[i] + '</span>' +
                  '<span class="semana__num">' + d.getDate() + '</span>' +
                '</div>' +
                '<div class="semana__cuerpo">' +
                  (lista.length ? lista.map(Pintar.chip).join("") : '<span class="vacio-mini">—</span>') +
                '</div>' +
              '</div>';
    }
    html += '</div>';
    return html;
  },

  /* ---------- VISTA LISTA DE ENTREGAS ---------- */
  lista(estado, tareas) {
    const hoy = Fechas.hoy();
    const enSieteDias = Fechas.sumarDias(hoy, 8);

    const grupos = [
      { titulo: "Vencidas", clase: "grupo--mal", items: [] },
      { titulo: "Hoy", items: [] },
      { titulo: "Próximos 7 días", items: [] },
      { titulo: "Más adelante", items: [] },
      { titulo: "Sin fecha", items: [] },
      { titulo: "Terminadas, falta confirmar", clase: "grupo--ok", items: [] }
    ];

    tareas.forEach(function (t) {
      const listo = t.estadoActual === "hecha" || t.estadoActual === "entregada";
      if (listo) { grupos[5].items.push(t); return; }
      if (t.estadoActual === "confirmada") return;      // esas viven en Archivo
      if (!t.fechaObj) { grupos[4].items.push(t); return; }
      if (t.vencida) { grupos[0].items.push(t); return; }
      if (Fechas.mismoDia(t.fechaObj, hoy)) { grupos[1].items.push(t); return; }
      if (t.fechaObj < enSieteDias) { grupos[2].items.push(t); return; }
      grupos[3].items.push(t);
    });

    let html = '<div class="lista">';
    grupos.forEach(function (g) {
      if (!g.items.length) return;
      g.items.sort(function (a, b) {
        if (!a.fechaObj) return 1;
        if (!b.fechaObj) return -1;
        return a.fechaObj - b.fechaObj;
      });
      html += '<section class="grupo ' + (g.clase || "") + '">' +
                '<h2 class="grupo__titulo">' + esc(g.titulo) + ' <span class="grupo__n">' + g.items.length + '</span></h2>' +
                g.items.map(Pintar.renglon).join("") +
              '</section>';
    });
    if (html === '<div class="lista">') {
      html += '<p class="vacio">No hay entregas que mostrar con estos filtros.</p>';
    }
    return html + '</div>';
  },

  /* ---------- VISTA POR MATERIA ---------- */
  materias(estado, tareas) {
    let html = '<div class="materias">';
    CURSOS.forEach(function (c) {
      const suyas = tareas.filter(function (t) { return t.curso === c.clave && t.estadoActual !== "confirmada"; })
                          .sort(function (a, b) {
                            if (!a.fechaObj) return 1;
                            if (!b.fechaObj) return -1;
                            return a.fechaObj - b.fechaObj;
                          });
      const pend = suyas.filter(function (t) { return t.estadoActual === "pendiente" || t.estadoActual === "en-curso"; }).length;

      html += '<section class="materia" style="--c:' + esc(c.color) + '">' +
                '<header class="materia__cab">' +
                  '<span class="materia__clave">' + esc(c.clave) + '</span>' +
                  '<h2 class="materia__nombre">' + esc(c.nombre) + '</h2>' +
                  '<span class="materia__oficial">' + esc(c.nombreOficial) + ' · sem. ' + esc(c.semestre) + '</span>' +
                  '<span class="materia__cuenta">' + pend + ' pendiente' + (pend === 1 ? "" : "s") + '</span>' +
                '</header>' +
                (suyas.length
                  ? '<div class="materia__tareas">' + suyas.map(Pintar.renglon).join("") + '</div>'
                  : '<p class="vacio-mini">Todavía no hay entregas registradas.</p>') +
                '<details class="materia__temario"><summary>Unidades del programa</summary><ol>' +
                  c.unidades.map(function (u) { return '<li>' + esc(u.replace(/^[IVX]+\.\s*/, "")) + '</li>'; }).join("") +
                '</ol>' +
                (c.reglas.length
                  ? '<p class="materia__reglas-t">Reglas del curso</p><ul>' +
                    c.reglas.map(function (r) { return '<li>' + esc(r) + '</li>'; }).join("") + '</ul>'
                  : '') +
                '</details>' +
              '</section>';
    });
    return html + '</div>';
  },

  /* ---------- VISTA ARCHIVO ---------- */
  archivo(estado, tareas) {
    const confirmadas = tareas.filter(function (t) { return t.estadoActual === "confirmada"; });

    let html = '<div class="archivo">' +
      '<section class="aviso">' +
        '<h2>Cómo se borra una tarea</h2>' +
        '<p>Una entrega solo desaparece del archivo <code>js/tareas.js</code> cuando pasa por aquí, y llega aquí ' +
        'únicamente si confirmaste las dos cosas: que <strong>ya la hiciste</strong> y que <strong>ya la entregaste</strong>. ' +
        'Marcarla como hecha no la borra. Marcarla como entregada tampoco.</p>' +
        '<p>Cuando tengas tareas confirmadas, el botón de abajo te genera una versión nueva de <code>tareas.js</code> ' +
        'sin ellas. Reemplazas el archivo del repo con ese y listo: quedan fuera para siempre.</p>' +
      '</section>';

    if (confirmadas.length) {
      html += '<section class="grupo grupo--ok">' +
                '<h2 class="grupo__titulo">Listas para archivar <span class="grupo__n">' + confirmadas.length + '</span></h2>' +
                confirmadas.map(Pintar.renglon).join("") +
              '</section>';
    } else {
      html += '<p class="vacio">Nada confirmado todavía. Abre una entrega terminada y usa «Confirmar entrega».</p>';
    }

    html += '<div class="archivo__acciones">' +
              '<button class="btn btn--fuerte" data-accion="exportar-tareas">Descargar tareas.js sin las archivadas</button>' +
              '<button class="btn" data-accion="exportar-respaldo">Descargar respaldo (JSON)</button>' +
              '<button class="btn btn--peligro" data-accion="reiniciar">Borrar mi progreso guardado</button>' +
            '</div></div>';
    return html;
  }
};
