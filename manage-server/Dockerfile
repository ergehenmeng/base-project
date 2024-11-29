FROM openjdk:8-jdk-alpine
LABEL org.opencontainers.image.authors="ergehenmeng"
WORKDIR /home/app/manage
COPY target/manage-server.jar /home/app/manage/manage-server.jar
EXPOSE 10086
ENTRYPOINT ["java", "-jar", "manage-server.jar"]
