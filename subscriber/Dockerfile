FROM openjdk:8-jre

ENTRYPOINT ["java", "-jar", "/intech/subscriber-1.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /intech/subscriber-1.jar