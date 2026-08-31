/* ============================================================
   menu.js — el menú de tres niveles.
   ------------------------------------------------------------
   Los tres niveles viven en un atributo del <html>:
   data-menu="cerrado" | "flotante" | "fijo".
   El CSS reacciona a ese atributo, el JS solo lo cambia.

     cerrado   riel angosto, puros iconos.
     flotante  un clic. El panel se abre ENCIMA de la página;
               la página no se mueve. Un clic afuera lo cierra.
     fijo      dos clics seguidos. El panel se queda y empuja
               toda la página a un lado. Un clic afuera ya no
               lo cierra: se queda hasta que tú lo cierres.

   El truco del doble clic: NO se usa el evento "dblclick",
   porque ese llega tarde (el navegador espera a ver si viene
   el segundo clic, y mientras tanto el menú se sentiría lento).
   En vez de eso contamos los clics a mano: el primero abre de
   inmediato, y si llega un segundo dentro de la ventana de
   VENTANA_DOBLE_CLIC milisegundos, ascendemos a "fijo".
   ============================================================ */

const Menu = (function () {
  const VENTANA_DOBLE_CLIC = 350; // ms

  let nivel = "cerrado";
  let clics = 0;
  let reloj = null;

  const html = document.documentElement;

  function aplicar(nuevo) {
    nivel = nuevo;
    html.setAttribute("data-menu", nuevo);
    Estado.setMenu(nuevo);

    const etiqueta = document.getElementById("menuNivel");
    if (etiqueta) {
      const textos = {
        cerrado:  "cerrado",
        flotante: "flotante",
        fijo:     "fijo"
      };
      etiqueta.textContent = textos[nuevo];
      etiqueta.dataset.nivel = nuevo;
    }

    const boton = document.getElementById("menuToggle");
    if (boton) {
      boton.setAttribute("aria-expanded", nuevo === "cerrado" ? "false" : "true");
      boton.setAttribute("title",
        nuevo === "fijo"
          ? "Menú fijo — clic para cerrarlo"
          : "Un clic abre · dos clics lo dejan fijo");
    }
  }

  function alHacerClic(evento) {
    evento.stopPropagation();
    clics++;

    if (clics === 1) {
      // Primer clic: respuesta inmediata, sin esperar al segundo.
      aplicar(nivel === "cerrado" ? "flotante" : "cerrado");
      reloj = setTimeout(function () { clics = 0; }, VENTANA_DOBLE_CLIC);
      return;
    }

    // Segundo clic dentro de la ventana: fijar (o soltar si ya estaba fijo).
    clearTimeout(reloj);
    clics = 0;
    aplicar(nivel === "fijo" ? "cerrado" : "fijo");
  }

  /* Si el menú se cierra por otro camino (clic afuera, Escape, velo), hay que
     olvidar los clics contados. Si no, el siguiente doble clic llega con el
     contador a la mitad y el menú termina en el nivel equivocado. */
  function olvidarClics() {
    clearTimeout(reloj);
    clics = 0;
  }

  function clicAfuera(evento) {
    if (nivel !== "flotante") return;       // en "fijo" no se cierra solo
    const panel = document.getElementById("menu");
    if (panel && panel.contains(evento.target)) return;
    olvidarClics();
    aplicar("cerrado");
  }

  function teclas(evento) {
    if (evento.key === "Escape" && nivel !== "cerrado") { olvidarClics(); aplicar("cerrado"); }
    // Atajo: la tecla M alterna el menú (si no estás escribiendo en un campo).
    const activo = document.activeElement;
    const escribiendo = activo && /^(INPUT|TEXTAREA|SELECT)$/.test(activo.tagName);
    if (!escribiendo && (evento.key === "m" || evento.key === "M")) {
      olvidarClics();
      aplicar(nivel === "cerrado" ? "flotante" : "cerrado");
    }
  }

  return {
    iniciar() {
      // Arrancamos en el nivel que quedó la última vez, salvo "flotante":
      // un panel encima al abrir la página sería una sorpresa, así que
      // solo se recuerda "fijo".
      const guardado = Estado.menu();
      aplicar(guardado === "fijo" ? "fijo" : "cerrado");

      document.getElementById("menuToggle").addEventListener("click", alHacerClic);
      document.addEventListener("click", clicAfuera);
      document.addEventListener("keydown", teclas);

      // El velo oscuro (solo aparece en pantallas chicas) también cierra.
      const velo = document.getElementById("velo");
      if (velo) velo.addEventListener("click", function () { olvidarClics(); aplicar("cerrado"); });
    },
    nivel() { return nivel; },
    cerrar() { aplicar("cerrado"); },
    fijar() { aplicar("fijo"); }
  };
})();
