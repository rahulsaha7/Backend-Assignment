# Start with a base image that has Java 17 and other dependencies installed
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Copy the application source code into the container
COPY . /app

# Build the application using Gradle
RUN ./gradlew build

# Define the command to run when the container starts
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8005", "-jar", "build/libs/backend-assingment.jar"]


EXPOSE 8080
