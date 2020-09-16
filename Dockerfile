FROM maven:3.6-amazoncorretto-11 AS MAVEN_BUILD

ARG WORKING_DIR=/build

RUN yum install -y openssl

COPY src ${WORKING_DIR}/src
COPY pom.xml ${WORKING_DIR}

WORKDIR ${WORKING_DIR}
RUN mvn clean package -Dmaven.test.skip=true

FROM amazoncorretto:11
ENV APP_VER 0.0.1-SNAPSHOT
ENV APP_NAME "memcahe-client"
ENV APP_JAR_FILE ${APP_NAME}-${APP_VER}.jar
ENV ENV default
ENV LOG4J_CONFIG /app/log4j.properties
ENV APP_DIR /app

COPY --from=MAVEN_BUILD /build/target/${APP_JAR_FILE} ${APP_DIR}/${APP_JAR_FILE}