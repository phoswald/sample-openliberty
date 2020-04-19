FROM open-liberty as server-setup
USER root
RUN apt-get update \
 && apt-get install -y --no-install-recommends unzip
COPY maven/sample-openliberty.zip /tmp/app.zip 
RUN unzip /tmp/app.zip -d /tmp/app

################################################################################

FROM open-liberty
#RUN rm -rf /liberty/usr
COPY --chown=1001:0 --from=server-setup /tmp/app/wlp/usr /liberty/usr
EXPOSE 9080 9443
