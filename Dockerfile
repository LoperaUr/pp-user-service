# Dockerfile simplificado para Spring Boot con Gradle
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Construir el jar
RUN ./gradlew bootJar --no-daemon

# Usar JRE para correr la app
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=0 /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]