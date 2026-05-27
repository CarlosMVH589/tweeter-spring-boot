# Etapa 1: Compilar la aplicación saltándose los tests por completo
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true -DskipTests

# Etapa 2: Crear la imagen ligera utilizando Eclipse Temurin
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/SpringBootSecurityPostgresqlApplication-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
