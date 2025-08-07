# Stage 1: Build ứng dụng Spring Boot
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run app
FROM amazoncorretto:21.0.4
WORKDIR /app

# Copy JAR từ build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (tuỳ app)
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
