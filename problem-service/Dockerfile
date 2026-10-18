FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY problem-service/target/problem-service-1.0.0.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
