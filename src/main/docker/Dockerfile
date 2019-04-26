FROM open-liberty as server-setup
COPY maven/sample-microprofile.zip /config/
USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends unzip \
    && unzip /config/sample-microprofile.zip \
    && mv /wlp/usr/servers/SampleServer/* /config/ \
    && mkdir /liberty/usr/shared \
    && mv /wlp/usr/shared/resources /liberty/usr/shared \
    && rm -rf /config/wlp \
    && rm -rf /config/sample-microprofile.zip 

################################################################################

FROM open-liberty
COPY --chown=1001:0 --from=server-setup /config/ /config/
COPY --chown=1001:0 --from=server-setup /liberty/usr/shared/ /liberty/usr/shared/
EXPOSE 9080 9443
