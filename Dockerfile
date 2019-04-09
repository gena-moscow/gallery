FROM openjdk:8-jdk-alpine

WORKDIR /opt

COPY target/gallery-0.1.0-SNAPSHOT.jar .
COPY entrypoint.sh .

RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]