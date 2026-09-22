FROM eclipse-temurin:25-jdk
COPY ./target/seMethods-1.0-SNAPSHOT.jar /tmp/app.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "/tmp/app.jar"]