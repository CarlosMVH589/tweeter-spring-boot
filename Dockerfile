# Etapa 1: Compilar la aplicación usando Maven y Java 17
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen ligera utilizando Eclipse Temurin (Java 17 Alpine)
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/SpringBootSecurityPostgresqlApplication-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
