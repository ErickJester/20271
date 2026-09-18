# SegundaOpcion — minisitio Nuevo Ingreso ESCOM (Apache y Nginx)

Práctica: *Descarga, creación y ejecución de imagen Docker del minisitio de nuevo ingreso de ESCOM*.
Sitio origen: <https://www.escom.ipn.mx/nuevoingreso27_1/> (ya descargado en `sitio/`, reutilizado de
`Primer parcial/tareas/tarea 2`, que es el mismo sitio).

## Requisito pendiente antes de empezar

**Docker no está instalado en esta máquina** (se verificó: no está en PATH, no hay Docker Desktop
instalado, tampoco está en la distro WSL Ubuntu). Instala Docker Desktop para Windows (usa el backend
WSL2) antes de correr lo siguiente:
<https://www.docker.com/products/docker-desktop/>

## Comandos exactos a correr y capturar

Parado dentro de esta carpeta (`Primer parcial/practicas/SegundaOpcion/`):

```bash
# --- Apache / httpd, puerto 8080 ---
docker build -f Dockerfile.apache -t nuevoingreso-apache .
docker run --name nuevoingreso-apache -d -p 8080:80 nuevoingreso-apache

# --- Nginx, puerto 8081 ---
docker build -f Dockerfile.nginx -t nuevoingreso-nginx .
docker run --name nuevoingreso-nginx -d -p 8081:80 nuevoingreso-nginx

# --- verificar que ambos contenedores quedaron arriba ---
docker ps
```

Luego abre en el navegador, con la URL visible en la captura:

- `http://localhost:8080` → servido por Apache
- `http://localhost:8081` → servido por Nginx

Ambos deben mostrar el mismo minisitio, porque es el mismo `sitio/` copiado a dos servidores distintos.

## Qué capturar para el reporte (según la rúbrica)

| # | Evidencia | Dónde va |
|---|---|---|
| 1 | Carpeta `sitio/` con su contenido a la vista (Explorador o `ls`/`dir`) — evidencia de descarga | `reporte/imagenes/evidencia_descarga.png` |
| 2 | Los dos Dockerfiles completos (ya están en texto en este folder, se pueden pegar directo) | ya integrados en `reporte.tex` |
| 3 | Terminal corriendo `docker build -f Dockerfile.apache ...` completo hasta el `Successfully tagged` | `reporte/imagenes/build_apache.png` |
| 4 | Terminal corriendo `docker build -f Dockerfile.nginx ...` completo | `reporte/imagenes/build_nginx.png` |
| 5 | Terminal corriendo ambos `docker run ...` y el `docker ps` mostrando los dos contenedores activos | `reporte/imagenes/run_ambos.png` |
| 6 | Navegador en `http://localhost:8080` con el sitio funcionando y la URL visible | `reporte/imagenes/navegador_apache.png` |
| 7 | Navegador en `http://localhost:8081` con el sitio funcionando y la URL visible | `reporte/imagenes/navegador_nginx.png` |

Cuando tengas las capturas, guárdalas con esos nombres en `reporte/imagenes/` — el `.tex` ya las
referencia por nombre, así que basta con soltarlas ahí y compilar.

## Al terminar

Actualiza el estado de `wad-docker-apache-nginx` en `agenda/js/tareas.js` a `"hecha"` una vez subido.
