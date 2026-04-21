# *** Stage liberty-setup ***
FROM open-liberty:${liberty.runtime.version}-kernel-slim-java17-openj9 AS liberty-setup

# Set up directory structure: only the server.xml is needed for features.sh, no WAR or drivers.
COPY --chown=1001:0 maven/config /config/

RUN features.sh

# *** Final stage
FROM code.phoswald.ch/philip/jre-25:latest

# Set up directory structure: copy server and features (from stage)
COPY --chown=daemon:daemon --from=liberty-setup /opt/ol /opt/ol

# Set up directory structure: symlinks, empty dirs
RUN ln -s /opt/ol/wlp/usr/servers/defaultServer /config && \
    ln -s /opt/ol/wlp                           /liberty && \
    ln -s /opt/ol/wlp/output/defaultServer      /output && \
    mkdir     /logs && \
    chmod 777 /logs

# Set up directory structure: application (from Maven output)
COPY --chown=daemon:daemon maven/config                   /config/
COPY --chown=daemon:daemon maven/sample-openliberty.war   /config/apps/
COPY --chown=daemon:daemon maven/shared                   /liberty/usr/shared/

ENV PATH=/opt/ol/wlp/bin:$PATH \
    JAVA_HOME=/usr/lib/jvm/java-25-openjdk

EXPOSE 8080 8443
USER daemon
CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]
