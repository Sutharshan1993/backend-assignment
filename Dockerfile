# Step 1: Build the application using Maven
FROM maven:3.8.6-openjdk-17 AS builder
WORKDIR /project
COPY . .
RUN mvn clean package -DskipTests


# Step 2: Create a lightweight runtime image
FROM azul/zulu-openjdk-alpine:17-jre
RUN mkdir /app
# Copy the built JAR file from the Maven build stage
COPY --from=builder /project/target/bayzdelivery-0.0.1-SNAPSHOT.jar /app/bayzdelivery-0.0.1-SNAPSHOT.jar
WORKDIR /app
CMD ["java", "-jar", "bayzdelivery-0.0.1-SNAPSHOT.jar"]
