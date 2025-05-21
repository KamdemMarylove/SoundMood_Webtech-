FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy build artifact
COPY build/libs/*.jar app.jar

# Expose port (Spring Boot default 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
