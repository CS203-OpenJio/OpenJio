# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk/openjdk11:alpine-jre

# Set the working directory in the container
WORKDIR /openjio/jio

# Copy the packaged JAR file into the container at /app
COPY target/myapp.jar /app/app.jar

# Expose the port your app will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]