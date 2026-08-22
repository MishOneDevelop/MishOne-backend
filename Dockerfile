# 🧱 Etapa de construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos solo pom.xml y resolvemos dependencias primero (mejora cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Ahora copiamos el código fuente
COPY src ./src

# Compilamos sin tests
RUN mvn clean package -DskipTests

# 🏁 Etapa final: runtime con JDK 21
FROM eclipse-temurin:21-jdk

# Creamos directorio de trabajo
WORKDIR /app

# Copiamos el .jar desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto por Spring Boot
EXPOSE 8080

# 🔁 Ejecutamos la app
ENTRYPOINT ["java", "-jar", "app.jar"]
