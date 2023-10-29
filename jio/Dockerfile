# Using java 17
FROM arm64v8/openjdk:17-oracle

WORKDIR /app

# Copy the folder into the container
COPY . .

RUN ./mvnw clean install -U -DskipTests 

ARG JAR_FILE=target/*.jar

# Copying JAR file
RUN cp ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","./app.jar"]