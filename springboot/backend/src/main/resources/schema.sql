DROP schema IF EXISTS `jpa_jbd`;

CREATE schema IF NOT EXISTS `jpa_jbd`;
use `jpa_jbd`;

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
	`ID` int(11) NOT NULL AUTO_INCREMENT,
	`USER_NAME` varchar(45) NOT NULL,
	`PASSWORD` varchar(45) NOT NULL,
	`EMAIL` varchar(100) DEFAULT NULL,
	`CREATED_TIME` datetime NOT NULL,
	`UPDATED_TIME` datetime DEFAULT NULL,
	`USER_TYPE` varchar(45) NOT NULL,
	`DOB` date NOT NULL,
	PRIMARY KEY (`ID`),
	UNIQUE KEY `USER_NAME_UNIQUE` (`USER_NAME`)
);
	
	
	
INSERT INTO `USER` VALUES 
     (1,'PeterM','ABC123abc*','peter@email.com','2020-03-17 07:13:30',NULL,'STUDENT','2020-03-17'),
     (2,'Mike','password','mike@email.com','2020-03-18 14:59:35',NULL,'EMPLOYEE','2020-03-18'),
     (3,'KingPeter','password','kingpeter@email.com','2020-03-19 12:19:15',NULL,'EMPLOYEE','2020-03-18');
     