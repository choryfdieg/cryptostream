-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (x86_64)
--
-- Host: 127.0.0.1    Database: cryptostream
-- ------------------------------------------------------
-- Server version	5.7.44

CREATE DATABASE IF NOT EXISTS cryptostream;
USE cryptostream;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `balances`
--

DROP TABLE IF EXISTS `balances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `balances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `currency` varchar(10) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `balances_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `balances`
--

LOCK TABLES `balances` WRITE;
/*!40000 ALTER TABLE `balances` DISABLE KEYS */;
INSERT INTO `balances` VALUES (1,1,'BTC',0.40,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(2,1,'SOL',9.70,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(3,2,'BTC',1.00,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(4,2,'SOL',15.00,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(5,3,'BTC',0.75,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(6,3,'SOL',5.00,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(7,4,'BTC',2.00,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(8,4,'SOL',20.00,'2024-11-20 19:01:23','2024-11-20 19:01:23'),(9,1,'USD',409975.58,'2024-11-20 19:16:07','2024-11-20 19:16:07'),(10,2,'USD',400000.00,'2024-11-20 19:16:07','2024-11-20 19:16:07'),(11,3,'USD',400000.00,'2024-11-20 19:16:07','2024-11-20 19:16:07'),(12,4,'USD',400000.00,'2024-11-20 19:16:07','2024-11-20 19:16:07');
/*!40000 ALTER TABLE `balances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'El usuario Juan Pérez ha registrado una SELL','2024-11-21 15:42:07','2024-11-21 15:42:07'),(2,'El usuario Juan Pérez ha registrado una BUY. Moneda: BTC, Cantidad: 0.1 ','2024-11-21 23:37:36','2024-11-21 23:37:36'),(3,'El usuario Juan Pérez ha registrado una SELL. Moneda: BTC, Cantidad: 0.2 ','2024-11-21 23:37:56','2024-11-21 23:37:56');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `transaction_type` varchar(10) NOT NULL,
  `currency` varchar(10) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,'BUY','BTC',0.50,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(2,1,'BUY','SOL',10.00,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(3,2,'BUY','BTC',1.00,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(4,2,'BUY','SOL',15.00,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(5,3,'BUY','BTC',0.75,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(6,3,'BUY','SOL',5.00,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(7,4,'BUY','BTC',2.00,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(8,4,'BUY','SOL',20.00,'2024-11-20 19:06:37','2024-11-20 19:06:37'),(11,1,'BUY','BTC',0.50,'2024-11-20 22:41:30','2024-11-20 22:41:30'),(12,1,'SELL','BTC',0.50,'2024-11-20 22:43:49','2024-11-20 22:43:49'),(13,1,'BUY','SOL',0.50,'2024-11-21 00:05:50','2024-11-21 00:05:50'),(14,1,'SELL','SOL',0.50,'2024-11-21 00:06:06','2024-11-21 00:06:06'),(15,1,'SELL','SOL',0.10,'2024-11-21 15:05:20','2024-11-21 15:05:20'),(16,1,'SELL','SOL',0.10,'2024-11-21 15:39:35','2024-11-21 15:39:35'),(17,1,'SELL','SOL',0.10,'2024-11-21 15:42:01','2024-11-21 15:42:01'),(18,1,'BUY','BTC',0.10,'2024-11-21 23:35:42','2024-11-21 23:35:42'),(19,1,'SELL','BTC',0.20,'2024-11-21 23:37:55','2024-11-21 23:37:55');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Juan Pérez','juan.perez@example.com','123456789','2024-11-20 19:01:19','2024-11-20 19:01:19'),(2,'María González','maria.gonzalez@example.com','987654321','2024-11-20 19:01:19','2024-11-20 19:01:19'),(3,'Carlos López','carlos.lopez@example.com','456789123','2024-11-20 19:01:19','2024-11-20 19:01:19'),(4,'Ana García','ana.garcia@example.com','321654987','2024-11-20 19:01:19','2024-11-20 19:01:19');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-22  1:31:35
