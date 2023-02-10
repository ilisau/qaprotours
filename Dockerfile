FROM openjdk:17-jdk-slim
WORKDIR /
COPY target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]