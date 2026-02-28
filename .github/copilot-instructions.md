# Copilot Instructions

## Repository Overview

This is a proof-of-concept demonstrating OpenTelemetry (OTel) observability with two independent Spring Boot 3.5.9 / Java 21 microservices. Each module is a self-contained Maven project — there is no parent POM.

## Build & Run Commands

Each service must be built and run from its own directory:

```bash
# Provider service (port 8080)
cd springboot-demo
mvn spring-boot:run

# Consumer/client service (port 8081)
cd springboot-client-demo
mvn spring-boot:run
```

**Run tests** (from the module directory):
```bash
mvn test
# Run a single test class:
mvn test -Dtest=SpringbootDemoApplicationTests
```

**Start the observability infrastructure** (required before running the apps):
```bash
cd podman
podman compose -f otel-compose.yml up
```

**Build Docker images** (springboot-demo only, three strategies available):
```bash
# Simple
docker build -f src/main/docker/Dockerfile.simple -t tuxtor/springboot-demo:simple .
# Layered (requires mvn clean package first)
docker build -f src/main/docker/Dockerfile.layers -t tuxtor/springboot-demo:layers .
# Multi-staged
docker build -f src/main/docker/Dockerfile.multistaged -t tuxtor/springboot-demo:multistaged .
```

## Architecture

```
springboot-client-demo (8081) ──OpenFeign──► springboot-demo (8080)
        │                                           │
        └──────── OTLP (HTTP) ──────────────────────┘
                                  │
                         OTel Collector (:4317/:4318)
                        /          |           \
               Prometheus      Tempo        Loki
                    \              |           /
                           Grafana (:3000)
```

- **`springboot-demo`** (provider): Exposes a JPA/H2-backed `AdmBook` CRUD REST API, a CPU-intensive Fibonacci endpoint, and a multi-level log endpoint. Uses `opentelemetry-spring-boot-starter` (OTel SDK directly).
- **`springboot-client-demo`** (consumer): Calls `springboot-demo` via a Spring Cloud OpenFeign client. Uses the Micrometer tracing bridge (`micrometer-tracing-bridge-otel`) rather than the OTel SDK directly.
- **OTel Collector** enriches all telemetry with the attribute `team: vorozco` via the `attributes` processor.

## Key Conventions

### Two Different OTel Integration Approaches
The two services intentionally use different OTel integration strategies:
- `springboot-demo` → `opentelemetry-spring-boot-starter` (OTel SDK BOM `2.20.1`), configured via `otel.*` properties
- `springboot-client-demo` → `micrometer-tracing-bridge-otel` + `micrometer-registry-otlp`, configured via `management.otlp.*` and `management.tracing.*` properties

Do not mix these patterns within a single module.

### Package Naming
- `springboot-demo`: `com.vorozco` (flat, no sub-package for app name)
- `springboot-client-demo`: `com.vorozco.springboot_client_demo` (underscore in sub-package)

### Logging Profiles
Both services use a two-profile Logback setup (`logback-spring.xml`):
- `default` profile → plain text console output
- `prod` profile → JSON output via `ch.qos.logback.classic.encoder.JsonEncoder`

Activate prod logging with `-Dspring.profiles.active=prod`.

### HTTP Test Files
Integration tests use IntelliJ HTTP Client files located in `src/test/resources/`:
- `springboot-demo`: `book_test.http` + `http-client.env.json`
- `springboot-client-demo`: `trace_test.http` + `http-client.env.json`

### Curl Smoke Tests
```bash
curl http://localhost:8080/fibo?n=45   # CPU load / trace test
curl http://localhost:8080/log         # log level test
curl http://localhost:8081/trace-demo  # distributed trace test (requires both services running)
```

### Grafana
Available at `http://localhost:3000` with anonymous admin access (no login required in the compose setup). Datasources are pre-provisioned via `podman/grafana.yml`.
