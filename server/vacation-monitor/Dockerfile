FROM openjdk:11-jre-slim

RUN apt-get update && apt-get install -y --no-install-recommends curl

ENV JAVA_OPTS=""

EXPOSE 8082

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
#ENTRYPOINT [ "java", "-jar", "/app.jar" ]

#HEALTHCHECK --start-period=10s --interval=5s --timeout=3s --retries=10 CMD curl --silent --fail --request GET http://localhost:8761/actuator/health | grep UP || exit 1
