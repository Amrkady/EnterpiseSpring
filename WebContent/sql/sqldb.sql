
CREATE TABLE `attachments` (
  `id` int(11) NOT NULL,
  `att_real_Name` varchar(128) NOT NULL,
  `att_name` varchar(64) NOT NULL,
  `att_size` double DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TABLE IF NOT EXISTS `expensis` (
  `id` int(11) NOT NULL,
  `expensis_type` int(11) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  `expensis_quantity` int(11) NOT NULL,
  `month_mdate` date DEFAULT NULL,
  `month_hdate` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `expensis_types` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `is_general` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_MANAGER'),
(4, 'ROLE_ACCOUNTANT');

CREATE TABLE IF NOT EXISTS `snd_srf_qbd` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `snd_mdate` date DEFAULT NULL,
  `snd_hdate` varchar(64) NOT NULL,
  `pay_type` varchar(45) DEFAULT NULL,
  `for_reason` varchar(45) DEFAULT NULL,
  `amount` decimal(32,2) DEFAULT NULL,
  `snd_type` int(11) DEFAULT NULL,
  `expensis_types_id` int(11) DEFAULT '0',
  `company_Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `companies` (
  `id` int(11) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `manager` varchar(32) DEFAULT NULL,
  `loction` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `commercialNo` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `taxs` (
  `id` int(11) NOT NULL,
  `tax_value` decimal(32,2) NOT NULL,
  PRIMARY KEY (`id`,`tax_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `taxs`
--

INSERT INTO `taxs` (`id`, `tax_value`) VALUES
(1, '15.00');

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `LOGIN_NAME` varchar(32) NOT NULL,
  `role_id` int(11) NOT NULL,
  `mng` int(11) NOT NULL DEFAULT '0',
  `phone` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `employees` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `iqama_NO` varchar(32)  NULL,
  `empNo` int(32) NOT NULL,
  `NATIONALITY` varchar(32)  NULL,
  `ENTRANCE_mDATE` date DEFAULT NULL,
  `ENTRANCE_hdate` varchar(64)  NULL
  `bankNo` int(11)  NULL DEFAULT '0',
  `work_location` varchar(45) DEFAULT NULL,
  `work_startmDate` date DEFAULT NULL,
  `work_starthDate` varchar(45) DEFAULT NULL,
  `birthmDate` date DEFAULT NULL,
  `birthhDate` varchar(45) DEFAULT NULL,
  `iqamaEndmDate` date DEFAULT NULL,
  `iqamaEndhDate` varchar(45) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `vacations` (
  `id` int(32) NOT NULL,
  `emp_id` int(32)  NULL,
  `dayNo` int(32) NOT NULL,
  `vac_mDate` date DEFAULT NULL,
  `vac_hDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
  )
 
CREATE TABLE IF NOT EXISTS `contracts` (
  `id` int(32) NOT NULL,
  `startmDate` date DEFAULT NULL,
  `starthDate` varchar(45) DEFAULT NULL,
  `endmDate` date DEFAULT NULL,
  `endhDate` varchar(45) DEFAULT NULL,
  `hourNo` int(32) NOT NULL,
  `lastbillNo` int(32)  NULL,
  `con_amount` int(32)  NULL,
  `rest_amount` int(32)  NULL,
  `company_Id` int(11)  NULL,
  PRIMARY KEY (`id`)
  ) 
  
  CREATE TABLE IF NOT EXISTS `contracts_employees` (
  `id` int(32) NOT NULL,
  `con_id` int(32)  NULL,
  `emp_id` int(32)  NULL,
  PRIMARY KEY (`id`)
  )