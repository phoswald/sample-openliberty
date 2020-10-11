FROM open-liberty:20.0.0.10-kernel-java8-openj9

COPY --chown=1001:0 maven/config                 /config/
COPY --chown=1001:0 maven/sample-openliberty.war /config/apps/
COPY --chown=1001:0 maven/shared                 /liberty/usr/shared/

# RUN configure.sh
