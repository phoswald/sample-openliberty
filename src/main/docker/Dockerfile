# *** Stage liberty-setup
FROM open-liberty:${liberty.runtime.version}-kernel-slim-java17-openj9 AS liberty-setup

COPY --chown=1001:0 maven/config                 /config/
COPY --chown=1001:0 maven/sample-openliberty.war /config/apps/
COPY --chown=1001:0 maven/shared                 /liberty/usr/shared/

RUN features.sh

# *** Final stage
FROM code.phoswald.ch/philip/jre-25:latest

COPY --chown=daemon:daemon --from=liberty-setup /opt/ol  /opt/ol
COPY --chown=daemon:daemon --from=liberty-setup /config  /config
COPY --chown=daemon:daemon --from=liberty-setup /liberty /liberty

ENV PATH=/opt/ol/wlp/bin:$PATH \
    JAVA_HOME=/usr/lib/jvm/java-25-openjdk

EXPOSE 8080 8443
USER daemon
CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]
