FROM openjdk:11
EXPOSE 8080
ADD target/util-common-service-Docker-Image.jar util-common-service-Docker-Image.jar
ENTRYPOINT ["java","-jar","/util-common-service-Docker-Image.jar"]