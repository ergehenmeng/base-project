FROM openjdk:8-jre
LABEL org.opencontainers.image.authors="ergehenmeng"
WORKDIR /home/app
COPY target/webapp-server.jar /home/app/webapp-server.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "webapp-server.jar"]