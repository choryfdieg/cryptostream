FROM azul/zulu-openjdk:21
WORKDIR /app
COPY target/notificationservice.jar /app/notificationservice.jar
EXPOSE 8083
CMD ["java", "-jar", "notificationservice.jar"]