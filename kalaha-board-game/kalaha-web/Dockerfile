FROM openjdk:8-jre-alpine
COPY target/kalaha-web-0.0.1-SNAPSHOT.war .
EXPOSE $PORT
EXPOSE $DEBUG_PORT
CMD /usr/bin/java -Dlogging.path=/log/ -DLOG_LEVEL=DEBUG -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8013 -Xmx400m -Xms400m -jar kalaha-web-0.0.1-SNAPSHOT.war

