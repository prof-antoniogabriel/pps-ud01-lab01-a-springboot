# Proyecto A · API de avisos de seguridad (Spring Boot)

Proyecto didáctico para **UD01-LAB01. Radiografía de proyectos**. Una pequeña API HTTP permite listar, consultar y registrar avisos de seguridad; usa una base de datos de prueba en memoria.

## Reglas del laboratorio

- Trabaja exclusivamente sobre los repositorios facilitados y en tu propio equipo o laboratorio autorizado.
- Para completar el cuestionario Moodle, **identifica los archivos y las líneas de código** que justifican cada respuesta. Este README explica cómo ejecutar la aplicación, pero no sustituye el análisis del código.
- No necesitas modificar el código ni entregar un informe.
- Los datos son de prueba. No publiques esta aplicación en Internet como servicio de producción.

## Preparar y explorar el proyecto

1. Descarga el ZIP del proyecto y extráelo.
2. Abre una terminal **dentro de la carpeta que contiene el archivo principal de dependencias**.
3. Abre la carpeta con VS Code, IntelliJ IDEA u otro editor.
4. Elige **una** de las dos formas de ejecución: local o Docker. No arranques ambas en el mismo puerto simultáneamente.

Los comandos siguientes asumen una terminal de Linux/macOS (o WSL en Windows). `curl` puede ejecutarse en otra terminal mientras el servidor sigue en marcha.

## Requisitos para ejecución local

- **JDK 21** y **Apache Maven 3.9+** instalados y disponibles en la terminal.
- Comprueba los comandos:

```bash
java -version
mvn -version
```

> En este ZIP no se incluye Maven Wrapper (`mvnw`): es necesario instalar Maven, o utilizar Docker sin instalar Java/Maven en el host.

## Opción 1 · Ejecutar con Maven

Desde la raíz del proyecto:

```bash
mvn spring-boot:run
```

La primera ejecución puede descargar dependencias de Maven Central y requiere Internet. Cuando aparezca el mensaje de inicio de Spring Boot, prueba:

```bash
curl -i http://localhost:8080/api/notices
curl -i http://localhost:8080/api/notices/1
```

Para detenerlo, pulsa `Ctrl+C` en la terminal del servidor.

## Opción 2 · Compilar un JAR y ejecutarlo

```bash
mvn clean package
java -jar target/radiografia-springboot-0.0.1-SNAPSHOT.jar
```

La compilación ejecuta las pruebas antes de crear el JAR. Si solo deseas verificar las pruebas:

```bash
mvn test
```

## Opción 3 · Ejecutar con Docker

Necesitas Docker Engine o Docker Desktop en funcionamiento:

```bash
docker build -t lab01-springboot .
docker run --rm --name lab01-springboot -p 8080:8080 lab01-springboot
```

En otra terminal: `curl -i http://localhost:8080/api/notices`. Para detenerlo: `Ctrl+C` o `docker stop lab01-springboot`. El Dockerfile compila mediante Maven en una etapa y ejecuta el JAR con Java en otra.

## Probar la API

| Método | Ruta | Resultado esperado |
| --- | --- | --- |
| GET | `/api/notices` | JSON con una lista de avisos |
| GET | `/api/notices/1` | JSON con un aviso, si existe |
| POST | `/api/notices` | Crea un aviso y devuelve HTTP 201 |

Crea un aviso con valores de gravedad **en mayúsculas** e incluyendo la descripción:

```bash
curl -i -X POST http://localhost:8080/api/notices \
  -H 'Content-Type: application/json' \
  -d '{"title":"Comprobar accesos","severity":"HIGH","description":"Revisar registros de acceso"}'
```

> Este proyecto no incluye la ruta `/health`; comprueba su estado consultando `/api/notices`. La consola H2 está configurada en `/h2-console`, con URL JDBC `jdbc:h2:mem:radiografia`, usuario `sa` y contraseña vacía, para uso exclusivamente local.

## Configuración, datos y almacenamiento

La configuración predeterminada usa el **puerto 8080** y H2 en memoria. Los registros de prueba se cargan al iniciar la aplicación; se pierden al terminarla. Si cambias el puerto, modifica también la URL de los comandos de prueba.

## Problemas frecuentes

- **`mvn: command not found`**: instala Maven o utiliza Docker.
- **Versión Java incorrecta**: instala/configura el JDK 21 y revisa `JAVA_HOME`.
- **Puerto 8080 ocupado**: detén el proceso que lo usa o cambia el puerto del servidor (por ejemplo, `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081` o `docker run --rm -p 8081:8080 lab01-springboot`).
- **Fallo al descargar dependencias**: comprueba conexión, proxy y repositorios Maven.
- **HTTP 400 al crear avisos**: revisa `title`, `description` y los valores admitidos para `severity`.
