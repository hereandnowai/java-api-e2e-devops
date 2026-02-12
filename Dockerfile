# Multi-stage Dockerfile for building and running the Calculator API
# Stage 1: Build the application using Maven + Eclipse Temurin JDK
ARG JAVA_VERSION=17
ARG APP_VERSION=1.0.0

FROM maven:3.9.5-eclipse-temurin-${JAVA_VERSION} AS builder
WORKDIR /workspace

# Improve layer caching by copying dependency descriptors first
COPY pom.xml ./

# Download dependencies to leverage Docker layer caching
RUN mvn -B -f pom.xml dependency:go-offline

# Copy source and build the application (skip tests for faster builds)
COPY src ./src
RUN mvn -B -f pom.xml clean package -DskipTests

# Stage 2: Runtime image (smaller JRE image)
FROM eclipse-temurin:${JAVA_VERSION}-jre AS runtime

# Runtime environment variables
ARG APP_VERSION=1.0.0
ENV APP_VERSION=${APP_VERSION}
ENV JAVA_OPTS=""
ENV APP_HOME=/app
WORKDIR ${APP_HOME}

# Copy the built artifact from the builder stage
COPY --from=builder /workspace/target/calculator-api-${APP_VERSION}.jar ${APP_HOME}/calculator-api-${APP_VERSION}.jar

# Install curl for health checks (kept minimal and cleaned up)
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

# Create a non-root user and switch to it for security
RUN addgroup --system appgroup && adduser --system --group appuser
USER appuser

# Expose the application port (matches application.yml)
EXPOSE 8080

# Healthcheck using the application port
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Use shell form entrypoint so JAVA_OPTS are expanded at runtime
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/calculator-api-${APP_VERSION}.jar"]
