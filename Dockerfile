FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/

RUN apt-get update && apt-get install -y dos2unix && \
    dos2unix gradlew && \
    chmod +x gradlew

COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --no-daemon || true

COPY src ./src

RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT:-8080}", "app.jar"]
