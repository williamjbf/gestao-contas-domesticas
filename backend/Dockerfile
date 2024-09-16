# stage:build app in docker
FROM maven AS build
COPY src /opt/app/src  
COPY pom.xml /opt/app
RUN mvn -f /opt/app/pom.xml clean package -DskipTests

# stage:build image
FROM openjdk:17-jdk-alpine
COPY --from=build /opt/app/target/gestao-contas-domesticas.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]