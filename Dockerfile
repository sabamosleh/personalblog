FROM java:8-jdk-alpine
LABEL maintainer="moslahsaba@gmail.com"
COPY ./target/personalblog-0.0.2.jar /usr/app/
WORKDIR /usr/app
EXPOSE 10222
ENTRYPOINT ["java","-jar","personalblog-0.0.2.jar"]
