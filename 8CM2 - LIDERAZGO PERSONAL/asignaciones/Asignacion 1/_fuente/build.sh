#!/bin/sh
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"
OUT="$(cd "$HERE/.." && pwd)"
CHROME="/c/Program Files/Google/Chrome/Application/chrome.exe"

# inlinar fuentes base64 en un HTML autocontenido
awk 'BEGIN{while((getline line < "'"$HERE"'/fonts.css")>0) css=css line "\n"}
     /\/\*FONTS\*\//{printf "%s", css; next} {print}' "$HERE/infografia.html" > "$HERE/build.html"

WINHTML=$(cygpath -w "$HERE/build.html")
WINPDF=$(cygpath -w "$OUT/Infografia - Concepto de Liderazgo.pdf")

"$CHROME" --headless=new --disable-gpu --no-sandbox --no-pdf-header-footer \
  --run-all-compositor-stages-before-draw --virtual-time-budget=8000 \
  --print-to-pdf="$WINPDF" "file:///$(echo "$WINHTML" | tr '\' '/')" 2>&1 | grep -viE "devtools|bytes written|^$" || true

pdftoppm -r 170 -png -singlefile "$OUT/Infografia - Concepto de Liderazgo.pdf" "$OUT/Infografia - Concepto de Liderazgo"
ls -l "$OUT" | grep -i infograf
