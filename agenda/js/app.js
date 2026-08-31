/* ============================================================
   app.js — el pegamento: estado de la vista, filtros, panel de
   detalle, alta de tareas y exportación del archivo.
   ============================================================ */

const App = {
  anio: new Date().getFullYear(),
  mes: new Date().getMonth(),
  vista: "mes",          // mes | semana | lista | materias | archivo
  diaSel: null,          // "YYYY-MM-DD"
  detalle: null,         // { tipo: "tarea"|"dia", id }
  filtros: {
    cursos: CURSOS.map(function (c) { return c.clave; }),
    estados: ["pendiente", "en-curso", "hecha", "entregada"],
    soloVencidas: false,
    busqueda: ""
  }
};

/* ---------- datos ---------- */

/* Junta las tareas del archivo con las que creaste desde la web
   y le cuelga a cada una lo que se calcula al vuelo. */
function todasLasTareas() {
  const ahora = new Date();
  const crudas = TAREAS.concat(Estado.extras().map(function (t) {
    const copia = Object.assign({}, t);
    copia.local = true;
    return copia;
  }));

  return crudas.map(function (t) {
    const copia = Object.assign({}, t);
    copia.fechaObj = Fechas.aFecha(t.entrega);
    copia.estadoActual = Estado.estadoDe(t);
    copia.vencida = !!copia.fechaObj && copia.fechaObj < ahora &&
                    copia.estadoActual !== "entregada" && copia.estadoActual !== "confirmada";
    return copia;
  });
}

function tareasVisibles() {
  const f = App.filtros;
  const q = f.busqueda.trim().toLowerCase();
  return todasLasTareas().filter(function (t) {
    if (f.cursos.indexOf(t.curso) === -1) return false;
    if (App.vista !== "archivo" && t.estadoActual === "confirmada") return false;
    if (App.vista !== "archivo" && f.estados.indexOf(t.estadoActual) === -1) return false;
    if (f.soloVencidas && !t.vencida) return false;
    if (q) {
      const heno = (t.titulo + " " + (t.descripcion || "") + " " + (t.entregable || "") + " " + t.curso).toLowerCase();
      if (heno.indexOf(q) === -1) return false;
    }
    return true;
  });
}

function tareaPorId(id) {
  const todas = todasLasTareas();
  for (let i = 0; i < todas.length; i++) if (todas[i].id === id) return todas[i];
  return null;
}

/* ---------- barra superior ---------- */
function pintarBarra() {
  const titulos = {
    mes: Fechas.MESES[App.mes].charAt(0).toUpperCase() + Fechas.MESES[App.mes].slice(1) + " " + App.anio,
    semana: "Semana del " + Fechas.larga(Fechas.inicioSemana(App.diaSel ? Fechas.aFecha(App.diaSel) : Fechas.hoy())),
    lista: "Todas las entregas",
    materias: "Por materia",
    archivo: "Archivo"
  };
  document.getElementById("tituloVista").textContent = titulos[App.vista];

  const navegable = App.vista === "mes" || App.vista === "semana";
  document.getElementById("navFechas").hidden = !navegable;

  // Resumen de arriba a la derecha
  const todas = todasLasTareas();
  const enSieteDias = Fechas.sumarDias(Fechas.hoy(), 8); // hoy + 7 días completos
  const activas = todas.filter(function (t) { return t.estadoActual !== "confirmada"; });

  const vencidas = activas.filter(function (t) { return t.vencida; }).length;
  const semana = activas.filter(function (t) {
    return t.fechaObj && !t.vencida && t.fechaObj < enSieteDias &&
           t.estadoActual !== "entregada";
  }).length;
  const pendientes = activas.filter(function (t) {
    return t.estadoActual === "pendiente" || t.estadoActual === "en-curso";
  }).length;
  const archivables = todas.filter(function (t) { return t.estadoActual === "confirmada"; }).length;

  document.getElementById("resumen").innerHTML =
    '<span class="pill' + (vencidas ? " pill--mal" : "") + '"><b>' + vencidas + '</b> vencidas</span>' +
    '<span class="pill"><b>' + semana + '</b> en 7 días</span>' +
    '<span class="pill"><b>' + pendientes + '</b> pendientes</span>' +
    '<span class="pill' + (archivables ? " pill--ok" : "") + '"><b>' + archivables + '</b> por archivar</span>';
}

/* ---------- menú lateral ---------- */
function pintarMenu() {
  // Vista activa
  const items = document.querySelectorAll("[data-vista]");
  for (let i = 0; i < items.length; i++) {
    items[i].classList.toggle("activo", items[i].dataset.vista === App.vista);
  }

  // Materias con su conteo de pendientes
  const todas = todasLasTareas();
  document.getElementById("menuCursos").innerHTML = CURSOS.map(function (c) {
    const n = todas.filter(function (t) {
      return t.curso === c.clave &&
             (t.estadoActual === "pendiente" || t.estadoActual === "en-curso");
    }).length;
    const activo = App.filtros.cursos.indexOf(c.clave) !== -1;
    return '<label class="curso' + (activo ? "" : " curso--off") + '" title="' + esc(c.nombreOficial) + '">' +
             '<input type="checkbox" data-curso="' + esc(c.clave) + '"' + (activo ? " checked" : "") + '>' +
             '<span class="punto" style="background:' + esc(c.color) + '"></span>' +
             '<span class="curso__nombre">' + esc(c.nombre) + '</span>' +
             '<span class="curso__n">' + n + '</span>' +
           '</label>';
  }).join("");

  // Filtros de estado
  document.getElementById("menuEstados").innerHTML = Object.keys(ESTADOS)
    .filter(function (k) { return k !== "confirmada"; })
    .map(function (k) {
      const activo = App.filtros.estados.indexOf(k) !== -1;
      return '<label class="estado-f' + (activo ? "" : " estado-f--off") + '">' +
               '<input type="checkbox" data-estado="' + k + '"' + (activo ? " checked" : "") + '>' +
               '<span class="punto" style="background:' + esc(ESTADOS[k].color) + '"></span>' +
               '<span>' + esc(ESTADOS[k].etiqueta) + '</span>' +
             '</label>';
    }).join("") +
    '<label class="estado-f' + (App.filtros.soloVencidas ? "" : " estado-f--off") + '">' +
      '<input type="checkbox" id="soloVencidas"' + (App.filtros.soloVencidas ? " checked" : "") + '>' +
      '<span class="punto" style="background:#dc2626"></span><span>Solo vencidas</span>' +
    '</label>';
}

/* ---------- vista principal ---------- */
function pintarVista() {
  const cont = document.getElementById("vista");
  const tareas = tareasVisibles();
  const fn = Pintar[App.vista];
  cont.innerHTML = fn ? fn(App, tareas) : "";
  cont.dataset.vista = App.vista;
}

function render() {
  pintarBarra();
  pintarMenu();
  pintarVista();
  pintarDetalle();
}

/* ---------- panel de detalle ---------- */
function abrirTarea(id) {
  App.detalle = { tipo: "tarea", id: id };
  pintarDetalle();
}

function abrirDia(clave) {
  App.diaSel = clave;
  App.detalle = { tipo: "dia", id: clave };
  pintarVista();
  pintarDetalle();
}

function cerrarDetalle() {
  App.detalle = null;
  pintarDetalle();
}

function pintarDetalle() {
  const panel = document.getElementById("detalle");
  if (!App.detalle) {
    panel.classList.remove("abierto");
    panel.innerHTML = "";
    return;
  }
  panel.classList.add("abierto");

  if (App.detalle.tipo === "dia") {
    const d = Fechas.aFecha(App.detalle.id);
    const delDia = tareasVisibles().filter(function (t) {
      return t.fechaObj && Fechas.clave(t.fechaObj) === App.detalle.id;
    }).sort(function (a, b) { return a.fechaObj - b.fechaObj; });

    panel.innerHTML =
      '<header class="detalle__cab">' +
        '<button class="cerrar" data-accion="cerrar-detalle" aria-label="Cerrar">×</button>' +
        '<p class="detalle__tipo">' + esc(Fechas.relativo(d)) + '</p>' +
        '<h2 class="detalle__titulo">' + esc(Fechas.larga(d)) + '</h2>' +
      '</header>' +
      '<div class="detalle__cuerpo">' +
        (delDia.length
          ? delDia.map(Pintar.renglon).join("")
          : '<p class="vacio-mini">Nada que entregar este día.</p>') +
        '<button class="btn btn--fuerte ancho" data-accion="nueva-en-dia" data-fecha="' + esc(App.detalle.id) + '">' +
          'Agregar entrega este día</button>' +
      '</div>';
    return;
  }

  const t = tareaPorId(App.detalle.id);
  if (!t) { cerrarDetalle(); return; }

  const c = cursoDe(t.curso);
  const pasos = Estado.pasosDe(t);
  const hechos = pasos.filter(Boolean).length;
  const puedeConfirmar = t.estadoActual !== "confirmada";

  panel.innerHTML =
    '<header class="detalle__cab" style="--c:' + esc(c.color) + '">' +
      '<button class="cerrar" data-accion="cerrar-detalle" aria-label="Cerrar">×</button>' +
      '<p class="detalle__tipo"><span class="punto" style="background:' + esc(c.color) + '"></span> ' +
        esc(c.clave + " · " + c.nombre) + ' — ' + esc(TIPOS[t.tipo] || t.tipo) + '</p>' +
      '<h2 class="detalle__titulo">' + esc(t.titulo) + '</h2>' +
      '<p class="detalle__fecha' + (t.vencida ? " detalle__fecha--mal" : "") + '">' +
        (t.fechaObj
          ? esc(Fechas.larga(t.fechaObj) + " · " + Fechas.hora(t.fechaObj)) +
            ' <span class="rel' + (t.vencida ? " rel--mal" : "") + '">(' + esc(Fechas.relativo(t.fechaObj)) + ')</span>' +
            (t.fechaPorConfirmar ? '<br><span class="duda">Fecha supuesta — confírmala con el profesor</span>' : '')
          : 'Sin fecha de entrega') +
      '</p>' +
    '</header>' +

    '<div class="detalle__cuerpo">' +

      '<div class="bloque">' +
        '<p class="bloque__t">Estado</p>' +
        '<div class="estados">' +
          Object.keys(ESTADOS).filter(function (k) { return k !== "confirmada"; }).map(function (k) {
            return '<button class="est' + (t.estadoActual === k ? " est--on" : "") + '"' +
                   ' data-set-estado="' + k + '" style="--e:' + esc(ESTADOS[k].color) + '">' +
                   esc(ESTADOS[k].etiqueta) + '</button>';
          }).join("") +
        '</div>' +
      '</div>' +

      (t.descripcion ? '<div class="bloque"><p class="bloque__t">Qué es</p><p>' + esc(t.descripcion) + '</p></div>' : '') +
      (t.entregable ? '<div class="bloque"><p class="bloque__t">Qué se entrega</p><p>' + esc(t.entregable) + '</p></div>' : '') +

      ((t.pasos && t.pasos.length)
        ? '<div class="bloque">' +
            '<p class="bloque__t">Pasos <span class="bloque__n">' + hechos + '/' + t.pasos.length + '</span></p>' +
            '<div class="barra-prog"><i style="width:' + (t.pasos.length ? (hechos / t.pasos.length * 100) : 0) + '%"></i></div>' +
            '<ul class="pasos">' + t.pasos.map(function (p, i) {
              return '<li><label class="paso' + (pasos[i] ? " paso--ok" : "") + '">' +
                     '<input type="checkbox" data-paso="' + i + '"' + (pasos[i] ? " checked" : "") + '>' +
                     '<span>' + esc(p) + '</span></label></li>';
            }).join("") + '</ul>' +
          '</div>'
        : '') +

      '<div class="bloque">' +
        '<p class="bloque__t">Mis notas</p>' +
        '<textarea id="nota" class="nota" rows="3" placeholder="Dudas, enlaces, con quién lo haces…">' +
          esc(Estado.notaDe(t.id)) + '</textarea>' +
      '</div>' +

      '<div class="bloque bloque--dato">' +
        (t.origen ? '<p><b>De dónde salió:</b> ' + esc(t.origen) + '</p>' : '') +
        (t.parcial ? '<p><b>Parcial:</b> ' + esc(t.parcial) + '</p>' : '') +
        (t.ruta ? '<p><b>Carpeta:</b> <code>' + esc(t.ruta) + '</code></p>' : '') +
        (t.local ? '<p class="duda">Creada desde la web — vive solo en este navegador hasta que exportes tareas.js</p>' : '') +
      '</div>' +

      (puedeConfirmar
        ? '<div class="confirmar">' +
            '<p class="bloque__t">Confirmar entrega</p>' +
            '<p class="confirmar__ayuda">Las dos casillas tienen que estar palomeadas. Hasta entonces la tarea no se puede quitar del archivo.</p>' +
            '<label class="check"><input type="checkbox" id="chkHecha"> Ya la hice</label>' +
            '<label class="check"><input type="checkbox" id="chkEntregada"> Ya la entregué</label>' +
            '<button class="btn btn--fuerte ancho" id="btnConfirmar" data-accion="confirmar" disabled>Confirmar y mandar a archivo</button>' +
          '</div>'
        : '<div class="confirmar confirmar--ok">' +
            '<p><b>Confirmada.</b> Hecha y entregada. Ya se puede quitar de <code>tareas.js</code> desde la vista Archivo.</p>' +
            '<button class="btn ancho" data-accion="desconfirmar">Me equivoqué, regrésala a entregada</button>' +
            (t.local ? '<button class="btn btn--peligro ancho" data-accion="borrar-local">Eliminar definitivamente</button>' : '') +
          '</div>') +

    '</div>';
}

/* ---------- ventana de nueva tarea ---------- */
function abrirModal(fechaSugerida) {
  const modal = document.getElementById("modal");
  const hoy = fechaSugerida || Fechas.clave(Fechas.hoy());
  modal.innerHTML =
    '<div class="modal__caja" role="dialog" aria-modal="true" aria-label="Nueva entrega">' +
      '<header class="modal__cab">' +
        '<h2>Nueva entrega</h2>' +
        '<button class="cerrar" data-accion="cerrar-modal" aria-label="Cerrar">×</button>' +
      '</header>' +
      '<form id="formTarea" class="modal__cuerpo">' +
        '<label class="campo"><span>Título</span>' +
          '<input name="titulo" required placeholder="Reporte de lectura — …"></label>' +
        '<div class="campo-fila">' +
          '<label class="campo"><span>Materia</span><select name="curso">' +
            CURSOS.map(function (c) { return '<option value="' + esc(c.clave) + '">' + esc(c.clave + " · " + c.nombre) + '</option>'; }).join("") +
          '</select></label>' +
          '<label class="campo"><span>Tipo</span><select name="tipo">' +
            Object.keys(TIPOS).map(function (k) { return '<option value="' + k + '">' + esc(TIPOS[k]) + '</option>'; }).join("") +
          '</select></label>' +
        '</div>' +
        '<div class="campo-fila">' +
          '<label class="campo"><span>Fecha de entrega</span><input type="date" name="fecha" value="' + esc(hoy) + '"></label>' +
          '<label class="campo"><span>Hora</span><input type="time" name="hora" value="' + esc(CONFIG.horaEntregaPorDefecto) + '"></label>' +
          '<label class="campo campo--chico"><span>Parcial</span><select name="parcial">' +
            '<option value="">—</option><option value="1">1</option><option value="2">2</option><option value="3">3</option>' +
          '</select></label>' +
        '</div>' +
        '<label class="check"><input type="checkbox" name="sinFecha"> Todavía no tiene fecha</label>' +
        '<label class="check"><input type="checkbox" name="porConfirmar"> La fecha es una suposición</label>' +
        '<label class="campo"><span>Qué hay que hacer</span><textarea name="descripcion" rows="2"></textarea></label>' +
        '<label class="campo"><span>Qué se entrega</span><input name="entregable" placeholder="PDF, código, exposición…"></label>' +
        '<label class="campo"><span>Pasos (uno por renglón)</span><textarea name="pasos" rows="3"></textarea></label>' +
        '<p class="modal__nota">Se guarda en este navegador. Para que quede en el archivo del repo, usa ' +
          '<b>Archivo → Descargar tareas.js</b> y reemplaza <code>js/tareas.js</code>.</p>' +
        '<div class="modal__pie">' +
          '<button type="button" class="btn" data-accion="cerrar-modal">Cancelar</button>' +
          '<button type="submit" class="btn btn--fuerte">Guardar entrega</button>' +
        '</div>' +
      '</form>' +
    '</div>';
  modal.classList.add("abierto");
  const primero = modal.querySelector("input[name=titulo]");
  if (primero) primero.focus();

  document.getElementById("formTarea").addEventListener("submit", function (e) {
    e.preventDefault();
    const d = new FormData(e.target);
    const titulo = String(d.get("titulo") || "").trim();
    if (!titulo) return;

    const sinFecha = d.get("sinFecha") === "on";
    const entrega = sinFecha ? null : (d.get("fecha") + "T" + (d.get("hora") || "23:00"));

    Estado.agregarExtra({
      id: idDesde(titulo),
      curso: d.get("curso"),
      titulo: titulo,
      tipo: d.get("tipo"),
      entrega: entrega,
      fechaPorConfirmar: d.get("porConfirmar") === "on",
      parcial: d.get("parcial") ? Number(d.get("parcial")) : null,
      estado: "pendiente",
      descripcion: String(d.get("descripcion") || "").trim(),
      entregable: String(d.get("entregable") || "").trim(),
      pasos: String(d.get("pasos") || "").split("\n")
              .map(function (s) { return s.trim(); })
              .filter(Boolean),
      origen: "Agregada desde la agenda",
      ruta: ""
    });
    cerrarModal();
    render();
  });
}

function cerrarModal() {
  const modal = document.getElementById("modal");
  modal.classList.remove("abierto");
  modal.innerHTML = "";
}

function idDesde(titulo) {
  const base = titulo.toLowerCase()
    .normalize("NFD").replace(/[̀-ͯ]/g, "")
    .replace(/[^a-z0-9]+/g, "-").replace(/^-+|-+$/g, "").slice(0, 32);
  return (base || "tarea") + "-" + Date.now().toString(36).slice(-4);
}

/* ---------- descargas ---------- */
function descargar(nombre, texto) {
  const blob = new Blob([texto], { type: "text/plain;charset=utf-8" });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = nombre;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  setTimeout(function () { URL.revokeObjectURL(url); }, 1000);
}

const CAMPOS_TAREA = ["id", "curso", "titulo", "tipo", "entrega", "fechaPorConfirmar",
                      "parcial", "estado", "descripcion", "entregable", "pasos", "origen", "ruta"];

function exportarTareas() {
  const vivas = todasLasTareas().filter(function (t) { return t.estadoActual !== "confirmada"; });
  const archivadas = todasLasTareas().length - vivas.length;

  const cuerpo = vivas.map(function (t) {
    const lineas = CAMPOS_TAREA.map(function (campo) {
      let valor = campo === "estado" ? t.estadoActual : t[campo];
      if (valor === undefined) valor = campo === "pasos" ? [] : null;
      return "    " + campo + ": " + JSON.stringify(valor);
    });
    return "  {\n" + lineas.join(",\n") + "\n  }";
  }).join(",\n");

  const encabezado =
    "/* ============================================================\n" +
    "   tareas.js — REGISTRO DE ENTREGAS DEL SEMESTRE\n" +
    "   Generado desde la agenda el " + new Date().toLocaleString("es-MX") + ".\n" +
    "   " + archivadas + " tarea(s) confirmada(s) quedaron fuera de este archivo.\n\n" +
    "   Una tarea solo se quita de aquí después de confirmar en la web\n" +
    "   que se hizo Y que se entregó. Ver README.md.\n" +
    "   ============================================================ */\n\n";

  descargar("tareas.js", encabezado + "const TAREAS = [\n" + cuerpo + "\n];\n");
}

/* ---------- eventos ---------- */
function conectar() {
  // Navegación de fechas
  document.getElementById("mesAnterior").addEventListener("click", function () {
    if (App.vista === "semana") {
      const base = App.diaSel ? Fechas.aFecha(App.diaSel) : Fechas.hoy();
      App.diaSel = Fechas.clave(Fechas.sumarDias(base, -7));
    } else {
      App.mes--; if (App.mes < 0) { App.mes = 11; App.anio--; }
    }
    render();
  });
  document.getElementById("mesSiguiente").addEventListener("click", function () {
    if (App.vista === "semana") {
      const base = App.diaSel ? Fechas.aFecha(App.diaSel) : Fechas.hoy();
      App.diaSel = Fechas.clave(Fechas.sumarDias(base, 7));
    } else {
      App.mes++; if (App.mes > 11) { App.mes = 0; App.anio++; }
    }
    render();
  });
  document.getElementById("irHoy").addEventListener("click", function () {
    const h = Fechas.hoy();
    App.anio = h.getFullYear(); App.mes = h.getMonth(); App.diaSel = Fechas.clave(h);
    render();
  });

  // Buscador
  document.getElementById("buscar").addEventListener("input", function (e) {
    App.filtros.busqueda = e.target.value;
    pintarVista();
  });

  // Botón de nueva entrega
  document.getElementById("btnNueva").addEventListener("click", function () { abrirModal(null); });

  // Menú lateral: cambio de vista y filtros
  document.getElementById("menu").addEventListener("click", function (e) {
    const vista = e.target.closest("[data-vista]");
    if (vista) {
      App.vista = vista.dataset.vista;
      if (Menu.nivel() === "flotante") Menu.cerrar();
      render();
      return;
    }
    const accion = e.target.closest("[data-accion]");
    if (accion && accion.dataset.accion === "nueva-tarea") abrirModal(null);
  });

  document.getElementById("menu").addEventListener("change", function (e) {
    const curso = e.target.dataset.curso;
    if (curso) {
      const i = App.filtros.cursos.indexOf(curso);
      if (i === -1) App.filtros.cursos.push(curso); else App.filtros.cursos.splice(i, 1);
      Estado.setFiltros(App.filtros);
      render();
      return;
    }
    const est = e.target.dataset.estado;
    if (est) {
      const i = App.filtros.estados.indexOf(est);
      if (i === -1) App.filtros.estados.push(est); else App.filtros.estados.splice(i, 1);
      Estado.setFiltros(App.filtros);
      render();
      return;
    }
    if (e.target.id === "soloVencidas") {
      App.filtros.soloVencidas = e.target.checked;
      Estado.setFiltros(App.filtros);
      render();
    }
  });

  // Vista principal: clic en tarea, en día, o en un botón de acción
  document.getElementById("vista").addEventListener("click", function (e) {
    const accion = e.target.closest("[data-accion]");
    if (accion) {
      const a = accion.dataset.accion;
      if (a === "exportar-tareas") { exportarTareas(); return; }
      if (a === "exportar-respaldo") { descargar("agenda-respaldo.json", Estado.exportar()); return; }
      if (a === "reiniciar") {
        if (confirm("Esto borra tu progreso guardado en este navegador (estados, pasos, notas y tareas creadas desde la web). ¿Seguro?")) {
          Estado.reiniciar(); render();
        }
        return;
      }
    }
    const chip = e.target.closest("[data-tarea]");
    if (chip) { abrirTarea(chip.dataset.tarea); return; }

    const dia = e.target.closest("[data-fecha]");
    if (dia) abrirDia(dia.dataset.fecha);
  });

  // Panel de detalle
  const panel = document.getElementById("detalle");
  panel.addEventListener("click", function (e) {
    const t = App.detalle && App.detalle.tipo === "tarea" ? tareaPorId(App.detalle.id) : null;

    const accion = e.target.closest("[data-accion]");
    if (accion) {
      const a = accion.dataset.accion;
      if (a === "cerrar-detalle") { cerrarDetalle(); return; }
      if (a === "nueva-en-dia") { abrirModal(accion.dataset.fecha); return; }
      if (a === "confirmar" && t) {
        Estado.setEstado(t.id, "confirmada");
        render();
        return;
      }
      if (a === "desconfirmar" && t) { Estado.setEstado(t.id, "entregada"); render(); return; }
      if (a === "borrar-local" && t) {
        if (confirm("Se elimina la tarea «" + t.titulo + "» de este navegador. Ya está confirmada como hecha y entregada. ¿Continuar?")) {
          Estado.borrarExtra(t.id);
          cerrarDetalle();
          render();
        }
        return;
      }
    }

    const chip = e.target.closest("[data-tarea]");
    if (chip) { abrirTarea(chip.dataset.tarea); return; }

    const est = e.target.closest("[data-set-estado]");
    if (est && t) { Estado.setEstado(t.id, est.dataset.setEstado); render(); }
  });

  panel.addEventListener("change", function (e) {
    const t = App.detalle && App.detalle.tipo === "tarea" ? tareaPorId(App.detalle.id) : null;
    if (!t) return;

    if (e.target.dataset.paso !== undefined) {
      Estado.togglePaso(t, Number(e.target.dataset.paso));
      pintarDetalle();
      return;
    }
    if (e.target.id === "chkHecha" || e.target.id === "chkEntregada") {
      const a = document.getElementById("chkHecha");
      const b = document.getElementById("chkEntregada");
      document.getElementById("btnConfirmar").disabled = !(a && a.checked && b && b.checked);
    }
  });

  panel.addEventListener("input", function (e) {
    if (e.target.id !== "nota") return;
    const t = App.detalle && App.detalle.tipo === "tarea" ? tareaPorId(App.detalle.id) : null;
    if (t) Estado.setNota(t.id, e.target.value);
  });

  // Modal
  document.getElementById("modal").addEventListener("click", function (e) {
    if (e.target.id === "modal") { cerrarModal(); return; }
    const accion = e.target.closest("[data-accion]");
    if (accion && accion.dataset.accion === "cerrar-modal") cerrarModal();
  });

  document.addEventListener("keydown", function (e) {
    if (e.key !== "Escape") return;
    if (document.getElementById("modal").classList.contains("abierto")) { cerrarModal(); return; }
    if (App.detalle) cerrarDetalle();
  });
}

/* ---------- arranque ---------- */
document.addEventListener("DOMContentLoaded", function () {
  const guardados = Estado.filtros();
  if (guardados) App.filtros = Object.assign(App.filtros, guardados);

  App.diaSel = Fechas.clave(Fechas.hoy());

  document.getElementById("marcaSemestre").textContent = CONFIG.semestre;
  document.getElementById("marcaPeriodo").textContent = CONFIG.periodo;

  Menu.iniciar();
  conectar();
  render();
});
