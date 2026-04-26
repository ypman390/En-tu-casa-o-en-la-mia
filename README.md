# En tu casa o en la mía

Proyecto de DAM - Aplicación web para compartir espacios entre usuarios (sofá, ducha, garaje...)

## Tecnologías usadas
- Java 17
- Servlets + JSP
- Apache Tomcat 11
- MariaDB
- Bootstrap 5.3
- Maven
- Docker

## Cómo ejecutar el proyecto

### 1. Clonar el repositorio
git clone https://github.com/ypman390/En-tu-casa-o-en-la-mia.git

### 2. Arrancar la base de datos con Docker
Necesitas tener Docker Desktop instalado y abierto como administrador.
docker compose up -d

### 3. Configurar la conexión
En ConexionDB.java cambia el puerto según uses:
- Docker: puerto 3307
- MariaDB local: puerto 3306

### 4. Desplegar en Tomcat
Abre el proyecto en IntelliJ y ejecuta con Tomcat.

## Usuarios de prueba
- admin / 1234 (ADMIN)
- user / 1234 (USER)

## Autor
Iván Acosta - DAM 2026