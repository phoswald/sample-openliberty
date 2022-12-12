FROM open-liberty:${liberty.runtime.version}-full-java17-openj9

# ENV KEYSTORE_REQUIRED "false"

COPY --chown=1001:0 maven/config                 /config/
COPY --chown=1001:0 maven/sample-openliberty.war /config/apps/
COPY --chown=1001:0 maven/shared                 /liberty/usr/shared/

# RUN configure.sh
