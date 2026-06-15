# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/livraria-doacoes-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will override with PORT env variable)
EXPOSE 8080

# Health check (optional but recommended)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/usuarios || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

