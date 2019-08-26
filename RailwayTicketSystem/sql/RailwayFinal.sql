CREATE DATABASE  IF NOT EXISTS `railwaydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `railwaydb`;
-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: railwaydb
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carriage_types`
--

DROP TABLE IF EXISTS `carriage_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carriage_types` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carriage_types`
--

LOCK TABLES `carriage_types` WRITE;
/*!40000 ALTER TABLE `carriage_types` DISABLE KEYS */;
INSERT INTO `carriage_types` VALUES (1,'Coupe'),(2,'Reserved Seat'),(3,'Common');
/*!40000 ALTER TABLE `carriage_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carriages`
--

DROP TABLE IF EXISTS `carriages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carriages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL,
  `free_seats` int(11) NOT NULL,
  `train_id` int(11) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tr_car_idx` (`train_id`),
  KEY `type_car_idx` (`type_id`),
  CONSTRAINT `tr_car` FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `type_car` FOREIGN KEY (`type_id`) REFERENCES `carriage_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carriages`
--

LOCK TABLES `carriages` WRITE;
/*!40000 ALTER TABLE `carriages` DISABLE KEYS */;
INSERT INTO `carriages` VALUES (1,1,76,1,554.55),(2,2,94,1,301),(3,3,94,1,247),(4,1,122,2,770),(5,2,31,2,380),(6,3,87,2,234),(31,1,32,3,600),(32,2,20,3,340),(33,3,11,3,280),(34,1,51,13,770),(35,2,20,13,390),(36,3,25,13,210);
/*!40000 ALTER TABLE `carriages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_UNIQUE` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_stations`
--

DROP TABLE IF EXISTS `route_stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route_stations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  `arrival_time` datetime NOT NULL,
  `depart_time` datetime NOT NULL,
  `wait_time` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `route_idx` (`route_id`),
  KEY `station_idx` (`station_id`),
  CONSTRAINT `route` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `station` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_stations`
--

LOCK TABLES `route_stations` WRITE;
/*!40000 ALTER TABLE `route_stations` DISABLE KEYS */;
INSERT INTO `route_stations` VALUES (1,1,1,'2018-11-18 20:27:00','2018-11-18 20:29:00',2),(2,1,30,'2018-11-18 22:01:00','2018-11-18 22:03:00',2),(3,3,38,'2018-11-16 02:56:00','2018-11-16 03:11:00',15),(4,6,38,'2018-11-15 17:01:00','2018-11-15 17:17:00',16),(15,5,38,'2018-11-20 17:01:00','2018-11-20 17:16:00',11),(16,10,38,'2018-11-17 01:14:00','2018-11-17 01:23:00',9),(17,5,30,'2018-11-17 01:14:00','2018-11-17 01:23:00',3);
/*!40000 ALTER TABLE `route_stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `routes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `init_station_id` int(11) NOT NULL,
  `depart_time` datetime NOT NULL,
  `arrival_station_id` int(11) NOT NULL,
  `arrival_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `init_station_idx` (`init_station_id`),
  KEY `dep_station_idx` (`arrival_station_id`),
  CONSTRAINT `dep_station` FOREIGN KEY (`arrival_station_id`) REFERENCES `stations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `init_station` FOREIGN KEY (`init_station_id`) REFERENCES `stations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

LOCK TABLES `routes` WRITE;
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` VALUES (1,1,'2018-11-18 18:13:00',2,'2018-11-18 22:31:00'),(2,1,'2018-11-14 10:21:00',4,'2018-11-14 13:43:00'),(3,3,'2018-11-15 12:13:00',2,'2018-11-16 06:08:00'),(5,2,'2018-11-20 14:19:00',5,'2018-11-20 23:00:00'),(6,4,'2018-11-15 12:13:00',5,'2018-11-15 22:34:00'),(10,3,'2018-11-16 08:15:00',1,'2018-11-17 06:53:00'),(11,1,'2018-11-18 22:33:00',2,'2018-11-19 08:13:00'),(12,1,'2018-11-18 09:13:00',5,'2018-11-18 17:42:00'),(13,1,'2018-11-18 22:01:00',2,'2018-11-19 08:01:00');
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `city` varchar(70) NOT NULL,
  `region` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'Kharkov Main','Kharkov','Kharkovskaya Obl','Ukraine'),(2,'Kiev Main','Kiev','Kievskaya Obl','Ukraine'),(3,'Lvov Main','Lvov','Lvovskaya Obl','Ukraine'),(4,'Poltava Main','Poltava','Poltavska Obl','Ukraine'),(5,'Одесса Главный','Одесса','Одесская Обл','Украина'),(30,'Kiev Darnitsa','Kiev','Kievskaya Obl','Ukraine'),(37,'Харьков-3','Харьков','Харьковская Обл','Украина'),(38,'Zhitomir Main','Zhitomir','Zhitomirskaya Obl','Ukraine');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `train_id` int(11) NOT NULL,
  `route_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `carriage_id` int(11) NOT NULL,
  `total_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`user_id`),
  KEY `train_idx` (`train_id`),
  KEY `route_idx` (`route_id`),
  KEY `user_idx1` (`user_id`),
  KEY `type_t_idx` (`type_id`),
  KEY `carriage_t_idx` (`carriage_id`),
  CONSTRAINT `carriage_t` FOREIGN KEY (`carriage_id`) REFERENCES `carriages` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `route_t` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `train_t` FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `type_t` FOREIGN KEY (`type_id`) REFERENCES `carriage_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_t` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,22,1,1,1,1,5545),(5,24,2,3,3,6,468),(8,22,13,13,1,35,1540),(9,22,3,11,1,33,600),(10,22,1,1,3,3,988),(11,22,3,11,2,32,1020),(12,2,2,3,2,5,1140),(13,24,1,1,1,1,2218.2),(14,22,13,13,2,35,1170),(15,22,1,1,2,2,903),(16,32,2,3,1,4,6160),(17,22,13,13,1,34,1540),(18,22,1,1,3,3,247),(19,22,1,1,3,3,494),(20,22,1,1,3,3,494),(21,22,13,13,1,34,1540),(22,22,1,1,2,2,903);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trains`
--

DROP TABLE IF EXISTS `trains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tr_route_idx` (`route_id`),
  CONSTRAINT `tr_route` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trains`
--

LOCK TABLES `trains` WRITE;
/*!40000 ALTER TABLE `trains` DISABLE KEYS */;
INSERT INTO `trains` VALUES (1,1),(2,3),(3,11),(4,12),(13,13);
/*!40000 ALTER TABLE `trains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin','Eugene','Koshevoy',1),(2,'user','user','Oleg','Ivanov',2),(3,'user2','user2','Dmitriy','Demin',2),(4,'user3','user3','Igor','Andreev',2),(5,'user4','user4','John','Doe',2),(10,'user5','user5','Gleb','Young',2),(12,'user6','user6','Yuri','Kim',2),(13,'user7','user7','Xin','Xhang',2),(20,'kenguru11','wwo27','Jack','Greaves',2),(21,'user10','user10','Graham','Royal',2),(22,'user9','user9','Vasiliy','Grigoriev',2),(24,'user11','user11','Pavel','Pavlov',2),(25,'user50','user50','Bill','Williams',2),(26,'user33','user33','Kei','Light',2),(28,'user8','user8','Login','Logon',2),(29,'user21','user21','User','Ruser',2),(30,'юзер','юзер','Жора','Арорян',2),(31,'неюзер','неюзер','Юзер','Юзер',2),(32,'клиент','клиент','Клиент','Клиент',2),(33,'юззз','юззз','Ввыаыв','Ллдавп',2);
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

-- Dump completed on 2018-11-13  3:13:28
