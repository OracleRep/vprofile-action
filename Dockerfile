FROM eclipse-temurin:11-jdk AS BUILD_IMAGE
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

WORKDIR /vprofile-project
COPY . .
RUN mvn -B -DskipTests install

FROM tomcat:9-jre11
LABEL Project="Vprofile"
LABEL Author="Smstep"

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=BUILD_IMAGE /vprofile-project/target/vprofile-v2.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
