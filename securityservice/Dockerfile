FROM azul/zulu-openjdk:21
WORKDIR /app
COPY target/securityservice.jar /app/securityservice.jar
EXPOSE 8080
CMD ["java", "-jar", "securityservice.jar"]