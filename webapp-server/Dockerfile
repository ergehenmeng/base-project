FROM openjdk:17-jdk-alpine
LABEL org.opencontainers.image.authors="ergehenmeng"
WORKDIR /home/app/webapp
COPY target/webapp-server.jar /home/app/webapp/webapp-server.jar
RUN addgroup -S webapp && adduser -S webapp -G webapp -d /home/app/manage
USER webapp
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "webapp-server.jar"]