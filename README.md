# CryptoStream

CryptoStream es una aplicación basada en **Maven** y **Spring Boot** que implementa una arquitectura de microservicios. Los microservicios se comunican de manera asíncrona a través de un tópico en **Kafka**.

## Arquitectura

El diseño de la arquitectura implementada puede ser visualizado en el siguiente [diagrama de arquitectura](https://drive.google.com/file/d/1wda7qFrXGkCtNQOV0E_Y16ZHulMTAf_0/view?usp=drive_link).

## Servicios Principales

### **1. Market Service**
- Consulta datos de criptomonedas desde la API pública de [CoinGecko Doc](https://docs.coingecko.com/reference/introduction).
- Expone la información mediante un servicio REST.
- Endpoints disponibles:
   - **GET /prices/all**: Consulta el valor de todas las criptomonedas en USD.
   - **GET /prices/{coin_id}**: Consulta el valor de una criptomoneda específica en USD.
   - **GET /history/{coin_id}**: Consulta el historial de valores de una criptomoneda en un rango de tiempo.
- Configuración:
   - Se levanta en el puerto `8081`.

### **2. Trading Service**
- Registra transacciones de compra y venta de criptomonedas.
- Mantiene el balance de usuarios en una base de datos y envía notificaciones de transacciones a través de Kafka.
- Endpoints disponibles:
   - **POST /trading/transactions**: Registra una compra/venta, actualiza el saldo y envía un mensaje al tópico de Kafka.
   - **GET /transactions/all**: Retorna todas las transacciones registradas.
   - **GET /transactions/{userId}**: Retorna las transacciones de un usuario específico.
   - **GET /trading/balances/{userId}**: Retorna el saldo de un usuario en criptomonedas y USD.
- Configuración:
   - Se levanta en el puerto `8082`.

### **3. Notification Service**
- Consume los mensajes de transacciones enviados al tópico de Kafka.
- Registra notificaciones en la base de datos para la tabla notifications.
- Configuración:
   - Realiza polling del tópico cada 300 segundos, procesando hasta 10 mensajes a la vez.
   - Se levanta en el puerto `8083`.

### **4. Security Service**
- Actúa como gateway central para la comunicación con los servicios de CryptoStream.
- Implementa autenticación mediante JWT para autorizar peticiones.
- Endpoints disponibles:
   - **POST /auth/login**: Simula la autenticación de usuarios y genera un token JWT.
- Configuración:
   - Se levanta en el puerto `8080`.

## Servicios Adicionales

### **MySQL**
- Configurado en el puerto `3306` dentro del contenedor Docker.
- Los microservicios se conectan usando la URL `mysql:3306`.
- Incluye un archivo `dump.sql` que crea las tablas necesarias y carga datos iniciales.
- Configuración disponible en el directorio `/mysql`.

### **Kafka**
- Configurado en el puerto `9092` dentro del contenedor Docker.
- Los microservicios se conectan usando la URL `kafka:9092`.
- Configuración disponible en el directorio `/kafka`.

## ¿Cómo ejecutar este proyecto?

### Prerrequisitos:
1. **Maven**  
   Instalar Maven localmente para compilar el código fuente.  
   [Link de descarga](https://maven.apache.org/download.cgi).


2. **Docker**  
   Instalar Docker localmente para levantar los servicios dockerizados.  
   [Link de descarga](https://docs.docker.com/engine/install/).

### Pasos para ejecutar:

1. **Crear una red Docker**
   - Ejecutar el comando:
     ```bash
     docker network create cryptostream_network
     ```

2. **Compilar los servicios**
   - Ubicarse en la raíz del proyecto `/b2b` y ejecutar:
     ```bash
     mvn clean install
     ```
   - Esto genera los archivos `.jar` de cada servicio en la carpeta `/target` de cada módulo.


3. **Levantar MySQL**
   - Ubicarse en el directorio `/b2b/mysql` y ejecutar:
     ```bash
     docker-compose up
     ```
   - Esto levanta el servicio MySQL e importa el archivo `dump.sql` con las tablas y datos iniciales.


4. **Levantar Kafka**
   - Ubicarse en el directorio `/b2b/kafka` y ejecutar:
     ```bash
     docker-compose up
     ```
   - Esto levanta el servicio Kafka necesario para la integración de notificaciones.

5. **Levantar los microservicios**
   - Ubicarse en la raíz del proyecto `/b2b` y ejecutar:
     ```bash
     docker-compose up
     ```
   - Esto levanta los cuatro microservicios mencionados: `market-service`, `trading-service`, `notification-service`, y `security-service`.


## Swagger - Integración OpenApi 3

Luego de levantados los servicios se puede consultar la documentacion de la API usando las URLS disponibles de swagger

Las urls se han dejado sin restriccion de acceso para efectos de pruebas

**Servicio Market**  
http://localhost:8081/swagger-ui/index.html

**Servicio Trading**  
http://localhost:8081/swagger-ui/index.html

La documentacion de las API esta disponible gracias a la integracion de swagger en la especificacion OpenApi3.
Dentro de los controladores REST de ambos servicios fue agregada la documentacion de cada endpoint, sus parametros y valores de retorno.

## Ejemplos de peticiones HTTP 

En el siguiente enlace puedes descargar una collection postman con ejemplos de las peticiones disponibles dentro de la aplicacion.
[Link de descarga](https://drive.google.com/drive/folders/1TTK8sTXZv19q6Fp-x6rAxsqGsIRUPL3h?usp=drive_link).

## Tablero de seguimiento 
Se usó Trello para realizar un split detallado de cada requerimiento y sus tareas relacionadas.
Puedes visualizar el tablero [Enlace de tablero en trello](https://trello.com/b/mFaKjQRm/cryptostream)



