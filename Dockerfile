# ============================================================
# ETAPA 1: BUILD
# ============================================================

# Imagen con Maven 3.9.11 y JDK 21.
# Esta etapa se utiliza únicamente para compilar y empaquetar
# la aplicación Spring Boot.
FROM maven:3.9.11-eclipse-temurin-21 AS build

# Directorio de trabajo dentro del contenedor de construcción.
WORKDIR /app

# Copia el archivo pom.xml.
# Aquí se definen las dependencias, plugins y configuración de Maven.
COPY pom.xml .

# Copia el código fuente del proyecto.
COPY src ./src

# Compila y empaqueta la aplicación.
# -DskipTests evita ejecutar las pruebas durante la construcción de la imagen.
# El resultado se genera dentro del directorio target/.
RUN mvn clean package -DskipTests


# ============================================================
# ETAPA 2: RUNTIME
# ============================================================

# Imagen más ligera que incluye únicamente el entorno necesario
# para ejecutar aplicaciones Java 21.
# No incluye Maven ni el JDK completo.
FROM eclipse-temurin:21-jre

# Directorio de trabajo de la aplicación en ejecución.
WORKDIR /app

# Copia únicamente el JAR generado en la etapa anterior.
# --from=build permite recuperar archivos de la etapa de construcción.
# El JAR se renombra dentro del contenedor como app.jar.
COPY --from=build /app/target/radiografia-springboot-0.0.1-SNAPSHOT.jar app.jar

# Documenta que la aplicación utiliza el puerto 8080.
# EXPOSE no publica el puerto automáticamente.
# Para acceder desde el equipo anfitrión habrá que ejecutar, por ejemplo:
# docker run -p 8080:8080 ...
EXPOSE 8080

# Comando que se ejecutará al arrancar el contenedor.
#
# java       -> ejecuta la JVM
# -jar       -> indica que se va a ejecutar un archivo JAR
# app.jar    -> artefacto generado por Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]