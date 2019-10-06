FROM openjdk:9

ADD ./build/libs/user-service-1.0.0.jar service.jar

EXPOSE 8080

CMD ["java", "-jar", "service.jar"]
