"""Genera placeholders temporales para las 7 capturas que pide la rubrica.
Correr una vez (python _gen_placeholders.py) para que el .tex compile antes de
tener las capturas reales; despues solo se sobrescriben los PNG con las capturas
de verdad usando el mismo nombre de archivo y se borra este script.
"""
from PIL import Image, ImageDraw

nombres = [
    "evidencia_descarga",
    "build_apache",
    "build_nginx",
    "run_ambos",
    "navegador_apache",
    "navegador_nginx",
]

for nombre in nombres:
    img = Image.new("RGB", (1000, 560), (235, 235, 235))
    d = ImageDraw.Draw(img)
    d.rectangle([4, 4, 995, 555], outline=(150, 150, 150), width=3)
    texto = "PENDIENTE: " + nombre + ".png"
    bbox = d.textbbox((0, 0), texto)
    w, h = bbox[2] - bbox[0], bbox[3] - bbox[1]
    d.text(((1000 - w) / 2, (560 - h) / 2), texto, fill=(90, 90, 90))
    img.save("imagenes/" + nombre + ".png")

print("Listo: 6 placeholders en imagenes/")
