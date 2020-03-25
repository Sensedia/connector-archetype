FROM sensedia/openjdk8-base:1.3
MAINTAINER Sensedia Dev Team <dev_team@sensedia.com>

COPY target/lib/* /sensedia/lib/
COPY target/*-runner.jar /sensedia/app.jar

CMD /usr/bin/java $JAVA_OPTS -jar app.jar