# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="dawolterbeek@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/recipe-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} recipe-0.0.1-SNAPSHOT.jar

# Run the jar file 
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8080","-jar","/recipe-0.0.1-SNAPSHOT.jar"]