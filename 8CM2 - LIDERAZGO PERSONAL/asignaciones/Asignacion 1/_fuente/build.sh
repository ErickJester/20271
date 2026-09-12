#!/bin/sh
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"
OUT="${OUT:-$(cd "$HERE/.." && pwd)}"
NAME="${NAME:-Infografia - Concepto de Liderazgo}"

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

# inlinar fuentes base64 en un HTML autocontenido
awk 'BEGIN{while((getline line < "'"$HERE"'/fonts.css")>0) css=css line "\n"}
     /\/\*FONTS\*\//{printf "%s", css; next} {print}' "$HERE/infografia.html" > "$HERE/build.html"

WINHTML=$(cygpath -w "$HERE/build.html")
WINPDF=$(cygpath -w "$OUT/$NAME.pdf")

"$CHROME" --headless=new --disable-gpu --no-sandbox --no-pdf-header-footer \
  --run-all-compositor-stages-before-draw --virtual-time-budget=8000 \
  --print-to-pdf="$WINPDF" "file:///$(echo "$WINHTML" | tr '\\' '/')" 2>&1 | grep -viE "devtools|bytes written|^$" || true

# PNG: pdftoppm si esta, si no PyMuPDF (que si esta instalado en esta maquina)
if command -v pdftoppm >/dev/null 2>&1; then
  pdftoppm -r 170 -png -singlefile "$OUT/$NAME.pdf" "$OUT/$NAME"
else
  python - "$OUT/$NAME.pdf" "$OUT/$NAME.png" <<'PY'
import sys, fitz
src, dst = sys.argv[1], sys.argv[2]
page = fitz.open(src)[0]
page.get_pixmap(dpi=170).save(dst)
PY
fi

ls -l "$OUT" | grep -i infograf
