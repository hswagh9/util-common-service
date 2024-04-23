FROM openjdk:11.0.8-jre-slim

EXPOSE 8080

ADD target/Util-Common-Service-Docker-Image.jar Util-Common-Service-Docker-Image.jar

ENTRYPOINT ["java","-jar","/Util-Common-Service-Docker-Image.jar"]