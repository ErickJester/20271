# Copia local de https://www.escom.ipn.mx/nuevoingreso27_1/ servida con Apache.
# Sitio 100% estatico: basta el servidor web + los archivos.

FROM httpd:2.4-alpine

# DocumentRoot de la imagen oficial de Apache
COPY . /usr/local/apache2/htdocs/

# Apache dentro del contenedor escucha en el 80.
# Al correr:  docker run --name darkescomni2027_1 -dit -p 3500:80 darkescomni
# la pagina queda en  http://localhost:3500
EXPOSE 80
