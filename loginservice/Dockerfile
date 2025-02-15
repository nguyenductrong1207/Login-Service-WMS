# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build file and download dependencies
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Expose the application port (adjust if needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/loginservice-0.0.1-SNAPSHOT.jar"]
