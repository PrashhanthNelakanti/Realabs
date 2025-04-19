# Stage 1: build with Maven + JDK 1.8
FROM maven:3.6.3-jdk-8 AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: run on JRE 1.8
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
