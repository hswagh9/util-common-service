FROM openjdk:11.0.8-jre-slim

EXPOSE 8080

ADD target/util-common-service-docker-image.jar util-common-service-docker-image.jar

ENTRYPOINT ["java","-jar","/util-common-service-docker-image.jar"]