
FROM tutum/tomcat
MAINTAINER Sven Walter <sven.walter@wltr.eu>

RUN rm /tomcat/webapps/* -rf
ADD thetvdb-tripleizer.war /tomcat/webapps/ROOT.war

EXPOSE 8080
