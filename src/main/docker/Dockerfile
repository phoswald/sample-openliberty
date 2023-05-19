FROM open-liberty:${liberty.runtime.version}-kernel-slim-java17-openj9

COPY --chown=1001:0 maven/config                 /config/
COPY --chown=1001:0 maven/sample-openliberty.war /config/apps/
COPY --chown=1001:0 maven/shared                 /liberty/usr/shared/

RUN features.sh

# RUN configure.sh
