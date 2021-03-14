# exchangeRateService
Currency Exchange Rate Service

# Steps to compile, run, and test the project

# Compile:
mvn clean install -DskipTests

# Run:
cd <jar file location / target build folder>
java -jar exchangerateservice.jar

# Access SWAGGER-UI
http://localhost:8080/swagger-ui/index.html#/

# Build docker image
cd <project root folder>
chmod +x build_docker_image.sh
./build_docker_image.sh
docker images

# Run as docker container
docker run -d --name ers -p 8080:8080 exchangerateservice:1.0
docker ps -a

# Run docker image on remove server running docker engine
copy exchangerateservice_1.0.tar file to remote server
docker load -i exchangerateservice_1.0.tar
docker images
docker run -d --name ers -p 8080:8080 exchangerateservice:1.0
docker ps -a
docker logs ers

