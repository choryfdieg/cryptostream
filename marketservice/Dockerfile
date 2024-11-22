FROM azul/zulu-openjdk:21
WORKDIR /app
COPY target/marketservice.jar /app/marketservice.jar
EXPOSE 8081
CMD ["java", "-jar", "marketservice.jar"]