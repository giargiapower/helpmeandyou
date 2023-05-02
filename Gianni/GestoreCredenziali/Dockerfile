FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
VOLUME /tmp
EXPOSE 8080
ARG DEPENDENCY=target
ADD ${DEPENDENCY}/*.jar gestore-credenziali.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gestore-credenziali.jar"]