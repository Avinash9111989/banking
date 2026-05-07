FROM maven:3.9.0-eclipse-temurin-21 as builder
WORKDIR /app
COPY user-service/pom.xml .
COPY common/pom.xml ../common/
COPY pom.xml ../
RUN mvn dependency:go-offline -B

COPY user-service/ .
COPY common/ ../common/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar user-service.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "user-service.jar"]
