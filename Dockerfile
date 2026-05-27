# Etapa 1: Compilar usando Maven con Java 21
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true -DskipTests

# Etapa 2: Imagen de ejecución ligera con Java 21 Alpine
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /target/SpringBootSecurityPostgresqlApplication-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
