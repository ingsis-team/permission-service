FROM gradle:8.10.0-jdk-21-and-22

# Install PostgreSQL client
USER root
RUN apt-get update && apt-get install -y postgresql-client

# Copy your project files
COPY . /home/gradle/src
WORKDIR /home/gradle/src

# Build the application
RUN gradle assemble

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/home/gradle/src/build/libs/PermissionService-0.0.1-SNAPSHOT.jar"]
