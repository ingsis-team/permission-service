FROM gradle:8.7.0-jdk17

# Copy your project files
COPY . /home/gradle/src
WORKDIR /home/gradle/src

# Build the application
RUN gradle assemble

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/home/gradle/src/build/libs/PermissionService-0.0.1-SNAPSHOT.jar"]
