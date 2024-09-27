# Use an official OpenJDK runtime as a parent image
# slim image is based on Debian and is generally more compatible across different platforms.
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/backend-service-0.0.1-SNAPSHOT.jar /app/backend-service-0.0.1-SNAPSHOT.jar

# Expose the port that Spring Boot will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "backend-service-0.0.1-SNAPSHOT.jar"]
