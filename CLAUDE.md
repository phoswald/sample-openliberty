# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build and run tests
mvn clean verify

# Run in Liberty dev mode (standalone)
mvn liberty:run

# Build Docker image and run
mvn clean verify -P docker
docker run -it --name sample-openliberty --rm \
  -p 8080:8080 -p 8443:8443 \
  -e APP_JDBC_URL=jdbc:h2:/databases/task-db \
  -v ./databases/:/databases \
  sample-openliberty:0.1.0-SNAPSHOT
```

There are no automated tests; `mvn clean verify` compiles and packages the WAR.

## Architecture

Jakarta EE 10 / MicroProfile 6.0 web application running on OpenLiberty (Java 17).

**Two functional modules**, each with a REST resource and an HTML controller:
- `com.github.phoswald.sample.sample` — demo endpoints (time, config, XML/JSON echo) at `/app/rest/sample/*`
- `com.github.phoswald.sample.task` — CRUD task management at `/app/rest/tasks/*`, with Thymeleaf-rendered HTML pages

**Stack:**
- JAX-RS 3.1 for both REST and HTML controllers
- Thymeleaf 3.1 for server-side HTML rendering (templates in `src/main/webapp/WEB-INF/`)
- JPA 3.1 / EclipseLink against H2; datasource JNDI name `jdbc/taskDS`; DDL auto-generated
- MicroProfile Config 3.0 for configuration (key: `app.sample.config`)

**Key config files:**
- `src/main/liberty/config/server.xml` — Liberty features, HTTP ports (8080/8443), datasource, and Liberty variables mapping to env vars (`APP_JDBC_URL`, `APP_JDBC_USERNAME`, `APP_JDBC_PASSWORD`, `APP_SAMPLE_CONFIG`)
- `src/main/resources/META-INF/persistence.xml` — JPA persistence unit `taskDS`
- `pom.xml` `<bootstrapProperties>` — sets Liberty variables for local `mvn liberty:run`

**MicroProfile endpoints:** `/health`, `/metrics`, `/openapi/`, `/openapi/ui/`
