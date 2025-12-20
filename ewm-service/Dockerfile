FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY target/ewm-server-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]