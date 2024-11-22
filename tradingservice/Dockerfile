FROM azul/zulu-openjdk:21
WORKDIR /app
COPY target/tradingservice.jar /app/tradingservice.jar
EXPOSE 8082
CMD ["java", "-jar", "tradingservice.jar"]