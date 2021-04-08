FROM adoptopenjdk/openjdk11

EXPOSE 8080

WORKDIR /home/deploy

COPY ./build/libs/test-resilience4j-0.0.1-SNAPSHOT.jar demo.jar

CMD ["java", "-jar", "demo.jar"]