-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: warehouse_login_service
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `forgot_password`
--

DROP TABLE IF EXISTS `forgot_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forgot_password` (
  `fpid` int NOT NULL AUTO_INCREMENT,
  `expiration_time` datetime(6) NOT NULL,
  `otp` int NOT NULL,
  `user_user_id` int DEFAULT NULL,
  PRIMARY KEY (`fpid`),
  UNIQUE KEY `UK436rcwp67sud355lgi3s4p1cv` (`user_user_id`),
  CONSTRAINT `FK4smi7oqy3gk1eji1gtnytl9gq` FOREIGN KEY (`user_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forgot_password`
--

LOCK TABLES `forgot_password` WRITE;
/*!40000 ALTER TABLE `forgot_password` DISABLE KEYS */;
/*!40000 ALTER TABLE `forgot_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `token_id` int NOT NULL AUTO_INCREMENT,
  `expiration_time` datetime(6) NOT NULL,
  `refresh_token` varchar(500) NOT NULL,
  `user_user_id` int DEFAULT NULL,
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `UKmw99w2d9yrljeaowdl0siv3e3` (`user_user_id`),
  CONSTRAINT `FKjpmlw49x98wb3sfpca2n03men` FOREIGN KEY (`user_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (3,'2024-12-04 09:39:15.003255','363f3c45-1aac-4707-8381-abfec2471e20',5),(6,'2024-12-04 16:15:53.536665','3e7be837-ee27-43dd-8c38-13b325923259',1),(8,'2024-12-04 17:11:16.230558','9da3d452-2d9b-4870-bd30-fe2012dc7ea8',9),(9,'2024-12-05 09:42:54.629096','a71668e4-20fd-4d64-a508-45540ea3d61e',11),(10,'2024-12-05 14:58:15.133968','d0c7e4a5-5304-47d5-8b92-b4a7790777fe',12),(11,'2024-12-05 14:59:31.052045','04606408-3d4d-45ad-9586-a77581cbcd1f',14),(17,'2025-01-05 10:19:19.220804','320c006b-e12d-4cf8-81d1-c05f3bf19202',6),(18,'2025-01-05 16:33:53.706386','b5985627-ca42-465a-9000-13fab0f56215',15);
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','MANAGER','STAFF') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@gmail.com','Nguyen Duc Trong','$2a$10$6sAgDe/htL2x/.iqZYmKSefvCnnALkM8adwGOrqevUzm5nRNXpPTa','ADMIN','admin'),(5,'testUpdate@gmail.com','Test Update','$2a$10$1fUKm/pmPCNEvK2FuELXKer4nzBk9mLMxTheXbS/cnPTS2HAcUV2O','MANAGER','testUpdate'),(6,'ductrong12072002@gmail.com','Nguyen Duc Trong','$2a$10$GduRwgTYi8mUKKR4ykiZGOtM0MlbkPeCty6k5AxjeeN1WT3qrNmmm','STAFF','ductrong'),(9,'baoly@gmail.com','Bao Ly','$2a$10$MFgx3/PUcky7omyV3UmvGO4W/WXWMlT4i/7kdow4cDDAs.xZSOys6','STAFF','baoly'),(11,'add@example.com','Test Add','$2a$10$4E3H4DmXdA/lFRF3.duOcudHH2OiHXLAodull53YnqXX9r3Al3g12','MANAGER','add'),(12,'trong.nguyen.cit20@eiu.edu.vn','Duc Trong','$2a$10$u6NtEtnNmnG3VpzYtEkjp.JVCM2eLRXiYIxANvZdS9Sbz8L6PZYAq','MANAGER','nguyenductrong'),(14,'ly@example.com','Ly','$2a$10$wnMDeYaOgYvWUGaGPgUC/O0CYyTsnPT1qH/4qofj2QI11uCjTqPpO','STAFF','ly'),(15,'trong@example.com','Trong','$2a$10$lpQ4j2N1Vm/YVTRHn.CxROCRKlbiLJo3haVOVHdKwSGEGxfVFtL.y','STAFF','trong');
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

-- Dump completed on 2024-12-06 23:42:19
