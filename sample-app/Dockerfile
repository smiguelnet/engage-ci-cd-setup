FROM openjdk:11-jdk-slim

#Set labels in order to inspect machine
LABEL maintainer="Sergio Miguel <sergio@smiguelnet>"
LABEL service.name="Sample App"
LABEL service.version=${project.version}

#Set work dir
WORKDIR /home/service

#Export webserver port
EXPOSE 8080

#Expose profiles as variables
ENV PROFILES default

#Set log path and expose it to be used by GC and Deap Dump
ENV LOG_PATH ${APP_FOLDER}/logs
#Set Java Ops and expose it so consumers can tune in application
ENV JAVA_OPS -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xms256m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_PATH
#Set Java Debug and expose it so consumers can tune in application
ENV JAVA_DEBUG_OPS -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$LOG_PATH/garbage-collection.log

#Add generated artifact from docker-maven-plugin
ADD ./target/${project.build.finalName}.jar /home/service/sample-app.jar

#Set entrypoint
ENTRYPOINT ["java", "-jar", "/home/service/sample-app.jar", "-Djava.security.egd=file:/dev/./urandom", "${JAVA_OPS}", "-Dspring.profiles.active=${PROFILES}"]


