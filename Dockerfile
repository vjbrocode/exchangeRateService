# Alpine Linux with OpenJDK JRE
FROM openjdk:8u212-alpine3.9

# update packages and add timezone package
RUN apk update && apk add tzdata

# copy Jar into image
COPY target/exchangerateservice.jar /app/exchangerateservice.jar

EXPOSE 8080

# Start the Exchange Rate Service
CMD java -jar /app/exchangerateservice.jar
