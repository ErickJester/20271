#!/bin/sh
# Genera el PDF de la investigacion a partir de investigacion.html.
# Uso:  sh build.sh          -> escribe el PDF en la carpeta de la asignacion
#       NAME="Otro" sh build.sh
#       CHROME=/ruta/chrome.exe sh build.sh
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"
OUT="${OUT:-$(cd "$HERE/.." && pwd)}"
NAME="${NAME:-Investigacion - La empresa}"

# navegador: el primero que exista (o CHROME=... por fuera)
if [ -z "$CHROME" ]; then
  for c in \
    "/c/Program Files/Google/Chrome/Application/chrome.exe" \
    "/c/Program Files (x86)/Google/Chrome/Application/chrome.exe" \
    "$LOCALAPPDATA/Google/Chrome/Application/chrome.exe" \
    "/c/Program Files/Microsoft/Edge/Application/msedge.exe" \
    "/c/Program Files (x86)/Microsoft/Edge/Application/msedge.exe" ; do
    [ -f "$c" ] && CHROME="$c" && break
  done
fi
[ -n "$CHROME" ] || { echo "No encontre Chrome ni Edge. Exporta CHROME=/ruta/al/exe" >&2; exit 1; }

# inlinar fuentes y logos en base64 -> HTML autocontenido
awk 'BEGIN{ while((getline l < "'"$HERE"'/fonts.css")>0) f = f l "\n"
            while((getline l < "'"$HERE"'/logos.css")>0) g = g l "\n" }
     /\/\*FONTS\*\//{printf "%s", f; next}
     /\/\*LOGOS\*\//{printf "%s", g; next}
     {print}' "$HERE/investigacion.html" > "$HERE/build.html"

WINHTML=$(cygpath -w "$HERE/build.html")
WINPDF=$(cygpath -w "$OUT/$NAME.pdf")

"$CHROME" --headless=new --disable-gpu --no-sandbox --no-pdf-header-footer \
  --run-all-compositor-stages-before-draw --virtual-time-budget=8000 \
  --print-to-pdf="$WINPDF" "file:///$(echo "$WINHTML" | tr '\\' '/')" 2>&1 \
  | grep -viE "devtools|bytes written|^$" || true

# PNG de la portada, para vista previa rapida
if command -v pdftoppm >/dev/null 2>&1; then
  pdftoppm -r 150 -png -f 1 -l 1 -singlefile "$OUT/$NAME.pdf" "$OUT/$NAME"
fi

echo "Listo:"
ls -l "$OUT" | grep -i "investigacion" || true
command -v pdfinfo >/dev/null 2>&1 && pdfinfo "$OUT/$NAME.pdf" | grep -iE "^Pages|^Page size"
