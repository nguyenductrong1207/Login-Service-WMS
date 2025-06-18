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
#ENV SPRING_PROFILES_ACTIVE=test

# Force an H2 in-memory DB at runtime:
#ENV SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false"
#ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME="org.h2.Driver"
#ENV SPRING_DATASOURCE_USERNAME="sa"
#ENV SPRING_DATASOURCE_PASSWORD=""
# Hibernate will auto-create and drop the schema:
#ENV SPRING_JPA_HIBERNATE_DDL_AUTO="create-drop"
#ENV SPRING_JPA_DATABASE_PLATFORM="org.hibernate.dialect.H2Dialect"

# Don’t bake in any SPRING_DATASOURCE_* here—Render will inject them.
# Only set the active profile to “prod” (or leave blank to use default).
ENV SPRING_PROFILES_ACTIVE=prod

#ENTRYPOINT ["java","-jar","app.jar"]
ENTRYPOINT sh -c "java -jar app.jar --server.port=${PORT}"