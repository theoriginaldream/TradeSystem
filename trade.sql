/*
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 8.0.24 : Database - trade
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`trade` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `trade`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `commentid` int NOT NULL AUTO_INCREMENT,
  `itemid` int NOT NULL,
  `userid` varchar(50) NOT NULL,
  `comment` varchar(240) NOT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`commentid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `consults` */

DROP TABLE IF EXISTS `consults`;

CREATE TABLE `consults` (
  `consultid` int NOT NULL AUTO_INCREMENT,
  `consult` varchar(200) NOT NULL,
  `userid` varchar(50) NOT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`consultid`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `headpicture` */

DROP TABLE IF EXISTS `headpicture`;

CREATE TABLE `headpicture` (
  `userid` varchar(30) NOT NULL,
  `headpicture` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Table structure for table `itempicture` */

DROP TABLE IF EXISTS `itempicture`;

CREATE TABLE `itempicture` (
  `itemid` int NOT NULL,
  `itempicture` varchar(80) DEFAULT NULL,
  `itempicture2` varchar(80) DEFAULT NULL,
  `itempicture3` varchar(80) DEFAULT NULL,
  `itempicture4` varchar(80) DEFAULT NULL,
  `itempicture5` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `itemid` int NOT NULL AUTO_INCREMENT,
  `itemname` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `schoolzone` varchar(30) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `detail` varchar(120) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `host` varchar(50) NOT NULL,
  `datetime` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `orderid` int NOT NULL AUTO_INCREMENT,
  `itemid` int NOT NULL,
  `buyer` varchar(50) NOT NULL,
  `seller` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `reply` */

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `replyid` int NOT NULL AUTO_INCREMENT,
  `reply` varchar(200) NOT NULL,
  `consultid` int NOT NULL,
  `userid` varchar(50) NOT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`replyid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `requires` */

DROP TABLE IF EXISTS `requires`;

CREATE TABLE `requires` (
  `ritemid` int NOT NULL AUTO_INCREMENT,
  `ritemname` varchar(50) NOT NULL,
  `detail` varchar(120) DEFAULT NULL,
  `price` varchar(30) DEFAULT NULL,
  `schoolzone` varchar(30) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `host` varchar(50) NOT NULL,
  PRIMARY KEY (`ritemid`)
) ENGINE=InnoDB AUTO_INCREMENT=1000020 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `shoppingcart` */

DROP TABLE IF EXISTS `shoppingcart`;

CREATE TABLE `shoppingcart` (
  `shoppingcartid` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) NOT NULL,
  `itemid` int NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`shoppingcartid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userid` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `phone` varchar(30) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `schoolzone` varchar(50) DEFAULT NULL,
  `introduce` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
