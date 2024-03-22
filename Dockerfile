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
ENTRYPOINT ["java", "-jar", "build/libs/gerenciador-pedidos-0.0.1-SNAPSHOT.jar"]