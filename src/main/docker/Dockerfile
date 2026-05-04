FROM code.phoswald.ch/philip/jre-25:latest

ENV TZ                  Europe/Zurich
#   LANG                de_CH.UTF-8
#   LC_ALL              de_CH.UTF-8
ENV JAVA_HOME           /usr/lib/jvm/default-jvm
#   JRE_CACERTS_PATH    ${JAVA_HOME}/lib/security/cacerts
ENV JAVA_TOOL_OPTIONS   -XX:MaxRAMPercentage=50
ENV PATH                ${PATH}:/opt/ol/wlp/bin
ENV ltpa_keys_password  unused

COPY --chown=daemon:daemon maven/wlp /opt/ol/wlp
CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]

USER daemon:daemon
EXPOSE 8080 8443
