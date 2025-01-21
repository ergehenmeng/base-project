FROM openjdk:17-jdk-alpine
LABEL org.opencontainers.image.authors="ergehenmeng"
WORKDIR /home/app/manage
COPY target/manage-server.jar /home/app/manage/manage-server.jar
RUN addgroup -S manage && adduser -S manage -G manage -d /home/app/manage
USER manage
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "manage-server.jar"]
