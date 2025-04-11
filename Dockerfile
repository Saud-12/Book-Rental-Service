FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/rent_read-0.0.1-SNAPSHOT.jar rentread.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "rentread.jar"]