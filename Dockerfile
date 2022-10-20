FROM openjdk:17
EXPOSE 8080
COPY /target/spring-boot-security.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]