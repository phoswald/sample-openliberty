# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build and package the WAR (no automated tests)
mvn clean verify

# Run in Liberty dev mode with hot reload
mvn liberty:dev

# Run in Liberty standalone mode
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

Jakarta EE 10 / MicroProfile 7.0 web application running on OpenLiberty (Java 25).

All JAX-RS resources are rooted under `/app` via `@ApplicationPath("/app")` in `RestApplication`.

**Two functional modules**, each with a REST resource and an HTML controller:
- `com.github.phoswald.sample.sample` — demo endpoints at `/app/rest/sample/*` (REST) and `/app/pages/sample` (HTML)
- `com.github.phoswald.sample.task` — CRUD task management at `/app/rest/tasks/*` (REST) and `/app/pages/tasks/*` (HTML)

**Stack:**
- JAX-RS 3.1 for both REST and HTML controllers
- Thymeleaf 3.1 for server-side HTML rendering — templates in `src/main/webapp/templates/`, loaded via `ClassLoaderTemplateResolver` (Liberty's WAR classloader exposes the webapp root on the classpath)
- JPA 3.1 / EclipseLink against H2; datasource JNDI name `jdbc/taskDS`; DDL auto-generated
- MicroProfile Config 3.0 for configuration (key: `app.sample.config`)

**View layer pattern:** `AbstractView<T>` is a generic Thymeleaf rendering base. Each template has a concrete view class (e.g. `TaskListView`, `TaskEditView`) and a corresponding view-model class (e.g. `TaskViewModel`). Controllers instantiate views directly (not via CDI) and call `.render(model)` to produce HTML strings returned as JAX-RS responses.

**Key config files:**
- `src/main/liberty/config/server.xml` — Liberty features, HTTP ports (8080/8443), datasource, and Liberty variables mapping to env vars (`APP_JDBC_URL`, `APP_JDBC_USERNAME`, `APP_JDBC_PASSWORD`, `APP_SAMPLE_CONFIG`)
- `src/main/resources/META-INF/persistence.xml` — JPA persistence unit `taskDS`
- `pom.xml` `<bootstrapProperties>` — sets Liberty variables for local `mvn liberty:run`/`liberty:dev`
- `src/main/docker/Dockerfile` — multi-stage build: stage 1 installs Liberty features using `open-liberty:${liberty.runtime.version}-kernel-slim-java17-openj9`; stage 2 uses `code.phoswald.ch/philip/jre-25:latest` (Alpine + Java 25) with Liberty copied in. The `liberty.runtime.version` build arg is sourced from the pom property.

**MicroProfile endpoints:** `/health`, `/metrics`, `/openapi/`, `/openapi/ui/`
