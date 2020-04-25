# sample-openliberty

Experiments with OpenLiberty and Docker, featuring:

- Static web content
- Dynamic web content using JAX-RS and Thymeleaf
- REST endpoints using JAX-RS 
- H2 database using JPA 

## Run Standalone

~~~
$ mvn clean verify
$ mvn liberty:run
$ mvn liberty:start
$ mvn liberty:stop
~~~

## Run with Docker

~~~
$ mvn clean package -P docker
$ docker run -it --name sample-openliberty --rm \
  -p 8080:8080 \
  -p 8443:8443 \
  -e APP_TASKDS_URL=jdbc:h2:/databases/task-db \
  -e APP_SAMPLE_CONFIG=ValueFromDocker \
  -v "$(pwd)/../databases":/databases \
  sample-openliberty:0.1.0-SNAPSHOT
~~~

## URLs

- http://localhost:8080/

~~~
$ curl 'http://localhost:8080/rest/sample/time'
$ curl 'http://localhost:8080/rest/sample/config'
$ curl 'http://localhost:8080/rest/sample/echo' -i -X POST \
  -H 'content-type: text/xml' \
  -d '<EchoRequest><input>This is CURL</input></EchoRequest>'
$ curl 'http://localhost:8080/rest/tasks' -i
$ curl 'http://localhost:8080/rest/tasks' -i -X POST \
  -H 'content-type: application/json' \
  -d '{"title":"Some task","description":"This is CURL","done":true}'
$ curl 'http://localhost:8080/rest/tasks/5b89f266-c566-4d1f-8545-451bc443cf26' -i
$ curl 'http://localhost:8080/rest/tasks/5b89f266-c566-4d1f-8545-451bc443cf26' -i -X PUT \
  -H 'content-type: application/json' \
  -d '{"title":"Some updated task","description":"This is still CURL","done":false}'
$ curl 'http://localhost:8080/rest/tasks/5b89f266-c566-4d1f-8545-451bc443cf26' -i -X DELETE
~~~

## TODO

- Es ist unklar, ob /liberty/usr aus dem Basisimage übernommen werden soll.
  Wenn es gelöscht wird (wie früher), startet der Server weil kein Keystore im Dropin gefunden wird.
  Dies, auch wenn im server.xml ein defaultKeyStore definiert wird.

- fabric8 Plugin aktualisieren

- Die bootstrapProperties aus dem pom.xml landen auch im Docker Image.
  Dies ist einerseits nicht sinnvoll und andererseits ein Problem, 
  da Bootstrap-Variablen nicht durch Environment-Variablen nicht überschrieben werden können.
