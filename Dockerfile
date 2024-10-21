FROM openjdk:17-jdk-alpine
LABEL authors="cdss4pcp"

WORKDIR app

COPY target/rule-modification-service-0.0.1-SNAPSHOT.jar rule-modification-service-0.0.1-SNAPSHOT.jar

#ADD lib lib
#ENV LIBPATH="/app/lib"
#ENV CLASSPATH="/app/lib/ST4-4.3.3.jar:/app/lib/antlr-runtime-3.5.3.jar:/app/lib/antlr4-4.10.1.jar:/app/lib/antlr4-runtime-4.10.1.jar:/app/lib/commons-lang3-3.12.0.jar:/app/lib/commons-text-1.10.0.jar:/app/lib/cql-3.2.0.jar:/app/lib/cql-to-elm-3.2.0.jar:/app/lib/cql-to-elm-cli-3.2.0.jar:/app/lib/elm-3.2.0.jar:/app/lib/elm-jaxb-3.2.0.jar:/app/lib/icu4j-69.1.jar:/app/lib/jackson-annotations-2.15.2.jar:/app/lib/jackson-core-2.15.2.jar:/app/lib/jackson-databind-2.15.2.jar:/app/lib/jackson-module-jaxb-annotations-2.15.2.jar:/app/lib/jakarta.activation-api-1.2.2.jar:/app/lib/jakarta.xml.bind-api-2.3.3.jar:/app/lib/javax.json-1.0.4.jar:/app/lib/jaxb2-basics-runtime-0.13.1.jar:/app/lib/jopt-simple-4.7.jar:/app/lib/model-3.2.0.jar:/app/lib/model-jaxb-3.2.0.jar:/app/lib/org.abego.treelayout.core-1.0.3.jar:/app/lib/org.eclipse.persistence.asm-2.7.7.jar:/app/lib/org.eclipse.persistence.core-2.7.7.jar:/app/lib/org.eclipse.persistence.moxy-2.7.7.jar:/app/lib/qdm-3.2.0.jar:/app/lib/quick-3.2.0.jar:/app/lib/slf4j-api-1.7.36.jar:/app/lib/ucum-1.0.3.jar:/app/lib/ucum-1.0.8.jar"

ENTRYPOINT ["java","-jar","rule-modification-service-0.0.1-SNAPSHOT.jar", "--spring.config.location=optional:classpath:/,optional:file:config/"]
