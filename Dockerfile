#
#
#ARG BUILD_HOME=/gerenciador-pedidos
#
##
## Gradle image for the build stage.
##
#FROM gradle:7-jdk-alpine as build-image
#MAINTAINER Linda Vieira
#
##
## Set the working directory.
##
#ARG BUILD_HOME
#ENV APP_HOME=$BUILD_HOME
#WORKDIR $APP_HOME
#
##
## Copy the Gradle config, source code, and static analysis config
## into the build container.
##
#COPY --chown=gradle:gradle build.gradle settings.gradle $APP_HOME/
#COPY --chown=gradle:gradle src $APP_HOME/src
#
##
## Build the application.
##
#RUN gradle --no-daemon build
#
##
## Java image for the application to run in.
##
#FROM openjdk:17-alpine
#
##
## Copy the jar file in and name it app.jar.
##
#ARG BUILD_HOME
#ENV APP_HOME=$BUILD_HOME
#COPY --from=build-image $APP_HOME/build/libs/gerenciador-pedidos-0.0.1-SNAPSHOT.jar app.jar
#
##
## The command to run when the container starts.
##
#ENTRYPOINT java -jar app.jar


# Use OpenJDK 11 as the base image
FROM openjdk:16-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .

# Copy the gradle wrapper files
COPY gradle/ ./gradle

# Copy the application source code
COPY src/ ./src

# Download and install Gradle (version 7)
RUN ./gradlew --version
RUN ./gradlew --no-daemon wrapper --gradle-version 7.0

# Build the Spring Boot application
RUN ./gradlew --no-daemon build

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "build/libs/gerenciador-pedidos.jar"]