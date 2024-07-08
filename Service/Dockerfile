FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
COPY --from=build /app/src/main/resources/application-docker.properties /app/application-docker.properties
EXPOSE 8088
CMD ["java", "-Dspring.config.location=/app/application-docker.properties", "-jar", "/app/app.jar"]
