FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY insurance-app/target/*.jar insurance.jar
ENTRYPOINT ["java","-jar","/insurance.jar"]