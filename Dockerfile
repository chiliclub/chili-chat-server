FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs
COPY ${JAR_FILE}/chilichat-*.jar chilichat.jar
ENTRYPOINT ["java","-jar","chilichat.jar"]