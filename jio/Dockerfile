# Using java 17
FROM openjdk:17-oracle

WORKDIR /app

# Copy the folder into the container
COPY . .

RUN chmod +x ./mvnw

SHELL ["/bin/bash", "-c"]

RUN ./mvnw clean install -U -DskipTests 

ARG JAR_FILE=target/*.jar

# Copying JAR file
RUN cp ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","./app.jar"]