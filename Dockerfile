FROM openjdk:17-slim
LABEL maintainer="contacts@gmail.com"
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
#COPY target/licensing-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]