FROM openjdk:8-jre
LABEL org.opencontainers.image.authors="ergehenmeng"
WORKDIR /home/app
COPY target/manage-server.jar /home/app/manage-server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "manage-server.jar"]
