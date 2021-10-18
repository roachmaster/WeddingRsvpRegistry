FROM openjdk:8-jdk-alpine
RUN apk add --no-cache bash
ARG JAR_FILE=build/libs/gatewayDemo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]