/* ============================================================
   estado.js — la memoria del navegador.
   ------------------------------------------------------------
   tareas.js dice QUÉ hay que entregar (eso lo edita una persona).
   Este archivo guarda CÓMO vas: estado de cada tarea, pasos
   palomeados, notas, tareas que creaste desde la web y en qué
   nivel dejaste el menú.

   Todo vive en localStorage, o sea en este navegador y en esta
   computadora. No hay servidor. Si abres la agenda en otro lado,
   empieza limpia (por eso existe el respaldo en JSON).
   ============================================================ */

const Estado = (function () {
  const LLAVE = "agenda-20271-v1";

  const vacio = {
    estados: {},   // { idTarea: "en-curso" }
    pasos: {},     // { idTarea: [true, false, ...] }
    notas: {},     // { idTarea: "texto" }
    extras: [],    // tareas creadas desde la web
    menu: "cerrado",
    filtros: null
  };

  let datos = cargar();

  function cargar() {
    try {
      const crudo = localStorage.getItem(LLAVE);
      if (!crudo) return JSON.parse(JSON.stringify(vacio));
      return Object.assign(JSON.parse(JSON.stringify(vacio)), JSON.parse(crudo));
    } catch (e) {
      // Modo incógnito, cookies bloqueadas, JSON corrupto: seguimos sin memoria.
      console.warn("No se pudo leer el estado guardado:", e);
      return JSON.parse(JSON.stringify(vacio));
    }
  }

  function guardar() {
    try {
      localStorage.setItem(LLAVE, JSON.stringify(datos));
    } catch (e) {
      console.warn("No se pudo guardar el estado:", e);
    }
  }

  return {
    /* --- estado de cada tarea --- */
    estadoDe(tarea) {
      return datos.estados[tarea.id] || tarea.estado || "pendiente";
    },
    setEstado(id, valor) {
      datos.estados[id] = valor;
      guardar();
    },

    /* --- checklist de pasos --- */
    pasosDe(tarea) {
      const total = (tarea.pasos || []).length;
      const guardados = datos.pasos[tarea.id] || [];
      const salida = [];
      for (let i = 0; i < total; i++) salida.push(!!guardados[i]);
      return salida;
    },
    togglePaso(tarea, indice) {
      const actual = this.pasosDe(tarea);
      actual[indice] = !actual[indice];
      datos.pasos[tarea.id] = actual;
      guardar();
    },

    /* --- notas libres --- */
    notaDe(id) {
      return datos.notas[id] || "";
    },
    setNota(id, texto) {
      if (texto) datos.notas[id] = texto;
      else delete datos.notas[id];
      guardar();
    },

    /* --- tareas creadas desde la web --- */
    extras() {
      return datos.extras.slice();
    },
    agregarExtra(tarea) {
      datos.extras.push(tarea);
      guardar();
    },
    borrarExtra(id) {
      datos.extras = datos.extras.filter(function (t) { return t.id !== id; });
      delete datos.estados[id];
      delete datos.pasos[id];
      delete datos.notas[id];
      guardar();
    },

    /* --- nivel del menú (cerrado / flotante / fijo) --- */
    menu() { return datos.menu || "cerrado"; },
    setMenu(nivel) { datos.menu = nivel; guardar(); },

    /* --- filtros de la barra lateral --- */
    filtros() { return datos.filtros; },
    setFiltros(f) { datos.filtros = f; guardar(); },

    /* --- respaldo completo --- */
    exportar() { return JSON.stringify(datos, null, 2); },
    importar(texto) {
      datos = Object.assign(JSON.parse(JSON.stringify(vacio)), JSON.parse(texto));
      guardar();
    },
    reiniciar() {
      datos = JSON.parse(JSON.stringify(vacio));
      guardar();
    }
  };
})();
