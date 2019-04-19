# sample-microprofile

## Build and Run (using Maven)

    $ mvn clean verify
    $ mvn liberty:run-server
    $ mvn liberty:start-server
    $ mvn liberty:stop-server

## Build and Run (using Docker)

    $ mvn clean package -P docker 
    $ docker run -d --name sample-server \
      -p 9080:9080 \
      -p 9443:9443 \
      -e SampleConfigSettingB=ValueFromDocker \
      sample-microprofile:0.1.0-SNAPSHOT
