FROM maven:3.9-amazoncorretto-21-alpine AS build

WORKDIR /app

COPY pom.xml ./
COPY src/ src/
RUN mvn package -DskipTests -q

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /app/target/*0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]