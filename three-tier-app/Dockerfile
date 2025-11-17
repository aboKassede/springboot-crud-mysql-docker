FROM maven:3.9.11-eclipse-temurin-17 as builder
WORKDIR /opt/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests 


FROM eclipse-temurin:17-jre as release
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/app.jar"]
