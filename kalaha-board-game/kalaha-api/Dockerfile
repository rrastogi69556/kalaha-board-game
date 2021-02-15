FROM openjdk:8-jre-alpine
COPY target/kalaha-api-0.0.1-SNAPSHOT.jar .
EXPOSE $PORT
EXPOSE $DEBUG_PORT
CMD /usr/bin/java -Dlogging.path=/log/ -DLOG_LEVEL=DEBUG -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8012 -Xmx400m -Xms400m -jar kalaha-api-0.0.1-SNAPSHOT.jar
