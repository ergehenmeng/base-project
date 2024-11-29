FROM openjdk:8-jdk-alpine
LABEL org.opencontainers.image.authors="ergehenmeng"
WORKDIR /home/app/webapp
COPY target/webapp-server.jar /home/app/webapp/webapp-server.jar
EXPOSE 10010
ENTRYPOINT ["java", "-jar", "webapp-server.jar"]