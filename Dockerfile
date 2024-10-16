FROM gradle:8.7.0-jdk17
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble
EXPOSE 8080
RUN mkdir -p /usr/local/newrelic
ADD ./newrelic-java/newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic-java/newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml
ENTRYPOINT ["java","-jar","-javaagent:/usr/local/newrelic/newrelic.jar","/home/gradle/src/build/libs/PermissionService-0.0.1-SNAPSHOT.jar"]