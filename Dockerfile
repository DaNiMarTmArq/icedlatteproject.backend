# Dockerfile

FROM amazoncorretto:21-alpine3.22-jdk

# Build argument for the jar file
ARG JAR_FILE=target/*.jar

# Copy the jar into the container
COPY ${JAR_FILE} app.jar

EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]