FROM maven:latest 

WORKDIR /workspace/suresh-job

COPY pom.xml .

RUN mvn dependency:resolve dependency:resolve-plugins

COPY src src

RUN mvn package

FROM openjdk:11-jdk

VOLUME /tmp

COPY ./target/user-0.0.1-SNAPSHOT.jar /app.jar

RUN apt update && \

    apt remove -y \

      bzip2 \

      mercurial \

      python \

      && \

    apt autoremove -y && \

    apt upgrade -y && \

    rm -rf /var/lib/apt/lists/*
 
ENV SERVER_PORT=8082

ENTRYPOINT ["java","--add-opens","java.base/java.lang=ALL-UNNAMED","-jar","/app.jar"] 
