# Stage 1: build with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN #mvn package
RUN mvn package -DskipTests

# Stage 2: slim runtime image
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /app/target/loginservice-0.0.1-SNAPSHOT.jar app.jar

# Use the test profile by default (in-memory H2)
ENV SPRING_PROFILES_ACTIVE=test

ENTRYPOINT ["java","-jar","app.jar"]