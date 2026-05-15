FROM eclipse-temurin:17-jre-alpine
WORKDIR /mgcss-track-7
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
