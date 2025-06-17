FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

COPY . .

RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /target/sabakalavro.jar .

EXPOSE 8080

CMD ["java", "-jar", "sabakalavro.jar"]
