# Use an official OpenJDK runtime as a parent image
FROM maven:3.8.3-openjdk-17 AS maven

# Set the working directory in the container
WORKDIR /openjio/jio

# Copy the folder into the container
COPY . /openjio/jio

RUN mvn clean package -DskipTests

# Using java 17
FROM openjdk:17-oracle

ARG JAR_FILE=/openjio/jio/target/*.jar

# Copying JAR file
COPY --from=maven ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar","dev.env"]