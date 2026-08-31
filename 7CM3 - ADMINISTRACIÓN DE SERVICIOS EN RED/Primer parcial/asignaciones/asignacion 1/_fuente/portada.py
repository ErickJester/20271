# -*- coding: utf-8 -*-
"""
Genera la portada de la Asignacion 1 (Direccionamiento IPv4), quita la nota de
cierre del documento original y une ambas cosas en el entregable final. El PDF
original no se modifica.

Formato de portada: mismo estilo que las portadas LaTeX usadas en 7CM2
(guinda IPN + azul ESCOM, centrado).

Uso:  python portada.py
Entrada : ../IPv4_Angel.pdf
Salida  : ../Asignacion1.pdf
"""

import io
import os

import fitz
from pypdf import PdfReader, PdfWriter
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas

AQUI = os.path.dirname(os.path.abspath(__file__))
ORIGINAL = os.path.join(AQUI, "..", "IPv4_Angel.pdf")
SALIDA = os.path.join(AQUI, "..", "Asignacion1.pdf")
PORTADA = os.path.join(AQUI, "portada.pdf")

COLOR_IPN = (0.5, 0.0, 0.13)
COLOR_ESCOM = (0.0, 0.5, 1.0)

ANCHO, ALTO = letter
MARGEN = 60.0

# Parrafos del documento original que no deben aparecer en el entregable.
# Se identifican por como empieza el bloque de texto.
A_ELIMINAR = ("Material de estudio elaborado",)

DATOS = {
    "institucion": "Instituto Politécnico Nacional",
    "escuela": "Escuela Superior de Cómputo",
    "materia": "Administración de servicios en red",
    "trabajo": "Asignación 1",
    "titulo": "Direccionamiento IPv4",
    "alumno": "Angel Frausto Robles",
    "boleta": "2019630177",
    "correo": "id634296@gmail.com",
    "grupo": "7CM3",
    "fecha": "30 de agosto de 2026",
}


def centrado(c, texto, y, fuente, tam, color):
    """Dibuja `texto` centrado en `y`, reduciendo el tamaño si no cabe."""
    disponible = ANCHO - 2 * MARGEN
    while tam > 6 and c.stringWidth(texto, fuente, tam) > disponible:
        tam -= 0.5
    c.setFont(fuente, tam)
    c.setFillColorRGB(*color)
    c.drawCentredString(ANCHO / 2.0, ALTO - y, texto)


def construir_portada(ruta):
    c = canvas.Canvas(ruta, pagesize=letter)
    c.setTitle("%s — %s" % (DATOS["trabajo"], DATOS["titulo"]))
    c.setAuthor(DATOS["alumno"])
    c.setSubject(DATOS["materia"])

    centrado(c, DATOS["institucion"], 110, "Helvetica-Bold", 21, COLOR_IPN)
    centrado(c, DATOS["escuela"], 140, "Helvetica-Bold", 15.5, COLOR_ESCOM)

    centrado(c, DATOS["materia"], 255, "Helvetica-Bold", 17, COLOR_IPN)

    centrado(c, DATOS["trabajo"], 330, "Helvetica-Bold", 15.5, COLOR_ESCOM)
    centrado(c, DATOS["titulo"], 362, "Helvetica-Bold", 21, COLOR_ESCOM)

    centrado(c, "Alumno: " + DATOS["alumno"], 480, "Helvetica-Oblique", 13.5, COLOR_IPN)
    centrado(c, "Boleta: " + DATOS["boleta"], 508, "Helvetica-Oblique", 13.5, COLOR_IPN)
    centrado(c, DATOS["correo"], 536, "Helvetica-Oblique", 13.5, COLOR_IPN)
    centrado(c, "Grupo: " + DATOS["grupo"], 564, "Helvetica-Oblique", 13.5, COLOR_IPN)

    centrado(c, DATOS["fecha"], 720, "Helvetica", 12, COLOR_IPN)

    c.showPage()
    c.save()


def contenido_depurado():
    """Devuelve el PDF original en memoria, sin los parrafos de A_ELIMINAR."""
    doc = fitz.open(ORIGINAL)
    quitados = 0

    for pagina in doc:
        for bloque in pagina.get_text("blocks"):
            texto = bloque[4].strip()
            if texto.startswith(A_ELIMINAR):
                rect = fitz.Rect(bloque[:4])
                pagina.add_redact_annot(rect)
                quitados += 1
        if pagina.first_annot is not None:
            pagina.apply_redactions()

    if quitados == 0:
        raise SystemExit("No se encontro el texto a eliminar; revisa A_ELIMINAR.")

    print("Parrafos eliminados del original:", quitados)
    datos = doc.tobytes()
    doc.close()
    return io.BytesIO(datos)


def main():
    construir_portada(PORTADA)

    escritor = PdfWriter()
    for pagina in PdfReader(PORTADA).pages:
        escritor.add_page(pagina)
    for pagina in PdfReader(contenido_depurado()).pages:
        escritor.add_page(pagina)

    escritor.add_metadata(
        {
            "/Title": "%s — %s" % (DATOS["trabajo"], DATOS["titulo"]),
            "/Author": "%s (%s)" % (DATOS["alumno"], DATOS["boleta"]),
            "/Subject": DATOS["materia"],
        }
    )

    with open(SALIDA, "wb") as f:
        escritor.write(f)

    os.remove(PORTADA)
    print("Generado:", os.path.normpath(SALIDA))


if __name__ == "__main__":
    main()
