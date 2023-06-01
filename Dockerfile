# Use the official OpenJDK 11 image as the base image
FROM openjdk:11-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the build artifact to the container
COPY target/beertap-0.0.1-SNAPSHOT.jar /app/beertap.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application when the container starts
CMD ["java", "-jar", "/app/beertap.jar"]
