FROM openjdk:17
MAINTAINER Linda Vieira

WORKDIR /gerenciador-pedidos
CMD ["./gradlew", "bootJar"]
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
