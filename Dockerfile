# Use the Maven image with Eclipse Temurin JDK 21 for the build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project, skipping tests
RUN mvn clean package -DskipTests

# Use the OpenJDK 21 slim image for the runtime stage
FROM openjdk:21-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/MyAgent-0.0.1-SNAPSHOT.jar demo.jar

# Expose the application port
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "demo.jar"]
