# build
FROM maven AS builder
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
COPY . .
RUN mvn clean package



# deploy
FROM openjdk:17-jdk-alpine
LABEL authors="cdss4pcp"

WORKDIR app

COPY --from=builder /usr/src/app/target/rule-modification-service-0.0.1-SNAPSHOT.jar rule-modification-service-0.0.1-SNAPSHOT.jar

EXPOSE 9090
ENTRYPOINT ["java","-jar","rule-modification-service-0.0.1-SNAPSHOT.jar", "--spring.config.location=optional:classpath:/,optional:file:config/"]
