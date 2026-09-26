#!/bin/sh
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"
OUT="$(cd "$HERE/.." && pwd)"
NAME="Linea del tiempo - Estilos de liderazgo"
CHROME="/c/Program Files/Google/Chrome/Application/chrome.exe"

awk 'BEGIN{while((getline line < "'"$HERE"'/fonts.css")>0) css=css line "\n"}
     /\/\*FONTS\*\//{printf "%s", css; next} {print}' "$HERE/linea.html" > "$HERE/build.html"

WINHTML=$(cygpath -w "$HERE/build.html")
WINPDF=$(cygpath -w "$OUT/$NAME.pdf")

"$CHROME" --headless=new --disable-gpu --no-sandbox --no-pdf-header-footer \
  --allow-file-access-from-files \
  --run-all-compositor-stages-before-draw --virtual-time-budget=10000 \
  --print-to-pdf="$WINPDF" "file:///$(echo "$WINHTML" | tr '\\' '/')" 2>&1 | grep -viE "devtools|bytes written|installwebapp|^$" || true

pdftoppm -r 150 -png -singlefile "$OUT/$NAME.pdf" "$OUT/$NAME"
ls -l "$OUT" | grep -i "$NAME"
