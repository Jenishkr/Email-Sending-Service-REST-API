# Multi-stage build: Build stage
FROM maven:3.9.4-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/enterprise-email-service-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
