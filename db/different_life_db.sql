-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Фев 10 2015 г., 22:05
-- Версия сервера: 5.5.25
-- Версия PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `different_life_db`
--
CREATE DATABASE `different_life_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `different_life_db`;
-- --------------------------------------------------------

--
-- Структура таблицы `addresses`
--

CREATE TABLE IF NOT EXISTS `addresses` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `apartment_number` varchar(255) DEFAULT NULL,
  `corps_number` varchar(255) DEFAULT NULL,
  `home_number` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `fk_contact_info_address` (`contact_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `attendant_persons`
--

CREATE TABLE IF NOT EXISTS `attendant_persons` (
  `att_person_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`att_person_id`),
  KEY `fk_contact_info_attperson_idx` (`contact_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `common_roles`
--

CREATE TABLE IF NOT EXISTS `common_roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `access_level` enum('GUEST','USER','EMPLOYEE','OWNER') DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `common_roles_rules_rel`
--

CREATE TABLE IF NOT EXISTS `common_roles_rules_rel` (
  `role` int(11) NOT NULL,
  `rule` int(11) NOT NULL,
  PRIMARY KEY (`role`,`rule`),
  KEY `fk_role_rr_rel_idx` (`role`),
  KEY `fk_rule_rr_rel_idx` (`rule`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `common_rules`
--

CREATE TABLE IF NOT EXISTS `common_rules` (
  `rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `component_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `common_user_info`
--

CREATE TABLE IF NOT EXISTS `common_user_info` (
  `user_info_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `sessionid` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_info_id`),
  KEY `fk_role_user_info_idx` (`role`),
  KEY `fk_contact_info_user_info_idx` (`contact_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `contact_info`
--

CREATE TABLE IF NOT EXISTS `contact_info` (
  `contact_id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `region` int(11) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  UNIQUE KEY `contact_id_UNIQUE` (`contact_id`),
  KEY `fk_region_idx` (`region`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `diagnosis`
--

CREATE TABLE IF NOT EXISTS `diagnosis` (
  `diagnosis_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`diagnosis_id`),
  UNIQUE KEY `diagnosis_id_UNIQUE` (`diagnosis_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `districts`
--

CREATE TABLE IF NOT EXISTS `districts` (
  `district_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`district_id`),
  UNIQUE KEY `district_id_UNIQUE` (`district_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `doctors`
--

CREATE TABLE IF NOT EXISTS `doctors` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`),
  KEY `fk_contact_info_doctor_idx` (`contact_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `documents`
--

CREATE TABLE IF NOT EXISTS `documents` (
  `document_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_created` datetime DEFAULT NULL,
  `date_modified` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `event` int(11) DEFAULT NULL,
  `extension` int(11) DEFAULT NULL,
  `posted_by_user` int(11) DEFAULT NULL,
  `access_level` enum('FOR_ALL','FOR_EMPLOYEES','FOR_OWNERS') DEFAULT NULL,
  PRIMARY KEY (`document_id`),
  KEY `fk_extension_document` (`extension`),
  KEY `fk_event_document` (`event`),
  KEY `fk_postedbyuser_document_idx` (`posted_by_user`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `events`
--

CREATE TABLE IF NOT EXISTS `events` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1024) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `date_time_start` datetime NOT NULL,
  `date_time_reg` datetime NOT NULL,
  `date_time_plan` datetime NOT NULL,
  `date_time_end` datetime NOT NULL,
  `notification_create_flag` varchar(5) DEFAULT NULL,
  `message_type` enum('TODO','IN_PROGRESS','DONE') DEFAULT NULL,
  `patient_history` int(11) DEFAULT NULL,
  `event_reason` int(11) DEFAULT NULL,
  `user_info` int(11) DEFAULT NULL,
  `posted_by_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `event_id_UNIQUE` (`event_id`),
  KEY `fk_ph_event_idx` (`patient_history`),
  KEY `fk_er_event_idx` (`event_reason`),
  KEY `fk_user_event_idx` (`user_info`),
  KEY `fk_postedbyuser_event_idx` (`posted_by_user`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `event_reasons`
--

CREATE TABLE IF NOT EXISTS `event_reasons` (
  `event_reason_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`event_reason_id`),
  UNIQUE KEY `event_reason_id_UNIQUE` (`event_reason_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `extensions`
--

CREATE TABLE IF NOT EXISTS `extensions` (
  `extension_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `extension_name` varchar(255) DEFAULT NULL,
  `icon_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`extension_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `medical_experts`
--

CREATE TABLE IF NOT EXISTS `medical_experts` (
  `medical_expert_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`medical_expert_id`),
  KEY `fk_contact_info_medexpert_idx` (`contact_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `notifications`
--

CREATE TABLE IF NOT EXISTS `notifications` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time_start` datetime DEFAULT NULL,
  `date_time_end` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `event` int(11) DEFAULT NULL,
  `user_info` int(11) DEFAULT NULL,
  `notification_type` enum('ACTIVE','NOT_ACTIVE','OVERDUE') DEFAULT NULL,
  `notification_state` enum('READ','NOT_READ') DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id_UNIQUE` (`notification_id`),
  KEY `fk_event_id_idx` (`event`),
  KEY `fk_userinfo_notification_idx` (`user_info`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `nurses`
--

CREATE TABLE IF NOT EXISTS `nurses` (
  `nurse_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`nurse_id`),
  KEY `fk_contact_info_nurse_idx` (`contact_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `patients`
--

CREATE TABLE IF NOT EXISTS `patients` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `birthdate` datetime DEFAULT NULL,
  `diagnosis` int(11) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  `disability` enum('GROUP1','GROUP2','GROUP3','CHILDREN') DEFAULT NULL,
  PRIMARY KEY (`patient_id`),
  UNIQUE KEY `patient_id_UNIQUE` (`patient_id`),
  KEY `fk_contact_info_idx` (`contact_info`),
  KEY `fk_diagnosis_idx` (`diagnosis`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `patient_histories`
--

CREATE TABLE IF NOT EXISTS `patient_histories` (
  `patient_histories_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient` int(11) DEFAULT NULL,
  `resolution` int(11) DEFAULT NULL,
  `attperson` int(11) DEFAULT NULL,
  `medical_expert` int(11) DEFAULT NULL,
  `nurse` int(11) DEFAULT NULL,
  `project` int(11) DEFAULT NULL,
  `curator` int(11) DEFAULT NULL,
  `lawyer` int(11) DEFAULT NULL,
  `doctor` int(11) DEFAULT NULL,
  `master_doctor` int(11) DEFAULT NULL,
  `status` enum('OPEN','CLOSED') DEFAULT NULL,
  PRIMARY KEY (`patient_histories_id`),
  UNIQUE KEY `patient_histories_id_UNIQUE` (`patient_histories_id`),
  KEY `fk_resolution_idx` (`resolution`),
  KEY `fk_medexpert_idx` (`medical_expert`),
  KEY `fk_nurse_idx` (`nurse`),
  KEY `fk_project_idx` (`project`),
  KEY `fk_attperson_ph_idx` (`attperson`),
  KEY `fk_patient_ph_idx` (`patient`),
  KEY `fk_curator_ph_idx` (`curator`),
  KEY `fk_lawyer_ph_idx` (`lawyer`),
  KEY `fk_doctor_ph_idx` (`doctor`),
  KEY `fk_masterdoctor_ph_idx` (`master_doctor`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `phone_numbers`
--

CREATE TABLE IF NOT EXISTS `phone_numbers` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(255) DEFAULT NULL,
  `phone_type` enum('HOME','MOBILE','WORK') DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`phone_id`),
  KEY `fk_contact_info_phone` (`contact_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `projects`
--

CREATE TABLE IF NOT EXISTS `projects` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id_UNIQUE` (`project_id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `regions`
--

CREATE TABLE IF NOT EXISTS `regions` (
  `region_id` int(11) NOT NULL AUTO_INCREMENT,
  `district` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`region_id`),
  UNIQUE KEY `region_id_UNIQUE` (`region_id`),
  KEY `fk_district_idx` (`district`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `resolutions`
--

CREATE TABLE IF NOT EXISTS `resolutions` (
  `resolution_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `project` int(11) DEFAULT NULL,
  PRIMARY KEY (`resolution_id`),
  UNIQUE KEY `resolution_id_UNIQUE` (`resolution_id`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  KEY `fk_project_resolution_idx` (`project`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `addresses`
--
ALTER TABLE `addresses`
  ADD CONSTRAINT `fk_contact_info_address` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `attendant_persons`
--
ALTER TABLE `attendant_persons`
  ADD CONSTRAINT `fk_contact_info_attperson` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `common_roles_rules_rel`
--
ALTER TABLE `common_roles_rules_rel`
  ADD CONSTRAINT `fk_role_rr_rel` FOREIGN KEY (`role`) REFERENCES `common_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_rule_rr_rel` FOREIGN KEY (`rule`) REFERENCES `common_rules` (`rule_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `common_user_info`
--
ALTER TABLE `common_user_info`
  ADD CONSTRAINT `fk_contact_info_user_info` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_role_user_info` FOREIGN KEY (`role`) REFERENCES `common_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `contact_info`
--
ALTER TABLE `contact_info`
  ADD CONSTRAINT `fk_region` FOREIGN KEY (`region`) REFERENCES `regions` (`region_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `doctors`
--
ALTER TABLE `doctors`
  ADD CONSTRAINT `fk_contact_info_doctor` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `fk_event_document` FOREIGN KEY (`event`) REFERENCES `events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_extension_document` FOREIGN KEY (`extension`) REFERENCES `extensions` (`extension_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_postedbyuser_document` FOREIGN KEY (`posted_by_user`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `fk_er_event` FOREIGN KEY (`event_reason`) REFERENCES `event_reasons` (`event_reason_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ph_event` FOREIGN KEY (`patient_history`) REFERENCES `patient_histories` (`patient_histories_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_postedbyuser_event` FOREIGN KEY (`posted_by_user`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_event` FOREIGN KEY (`user_info`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `medical_experts`
--
ALTER TABLE `medical_experts`
  ADD CONSTRAINT `fk_contact_info_medexpert` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `fk_event_notification` FOREIGN KEY (`event`) REFERENCES `events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_userinfo_notification` FOREIGN KEY (`user_info`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `nurses`
--
ALTER TABLE `nurses`
  ADD CONSTRAINT `fk_contact_info_nurse` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `patients`
--
ALTER TABLE `patients`
  ADD CONSTRAINT `fk_contact_info_patient` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_diagnosis` FOREIGN KEY (`diagnosis`) REFERENCES `diagnosis` (`diagnosis_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `patient_histories`
--
ALTER TABLE `patient_histories`
  ADD CONSTRAINT `fk_attperson_ph` FOREIGN KEY (`attperson`) REFERENCES `attendant_persons` (`att_person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_curator_ph` FOREIGN KEY (`curator`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_doctor_ph` FOREIGN KEY (`doctor`) REFERENCES `doctors` (`doctor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_lawyer_ph` FOREIGN KEY (`lawyer`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_masterdoctor_ph` FOREIGN KEY (`master_doctor`) REFERENCES `doctors` (`doctor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_medexpert_ph` FOREIGN KEY (`medical_expert`) REFERENCES `medical_experts` (`medical_expert_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_nurse_ph` FOREIGN KEY (`nurse`) REFERENCES `nurses` (`nurse_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_patient_ph` FOREIGN KEY (`patient`) REFERENCES `patients` (`patient_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_project_ph` FOREIGN KEY (`project`) REFERENCES `projects` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_resolution_ph` FOREIGN KEY (`resolution`) REFERENCES `resolutions` (`resolution_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `phone_numbers`
--
ALTER TABLE `phone_numbers`
  ADD CONSTRAINT `fk_contact_info_phone` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `regions`
--
ALTER TABLE `regions`
  ADD CONSTRAINT `fk_district` FOREIGN KEY (`district`) REFERENCES `districts` (`district_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `resolutions`
--
ALTER TABLE `resolutions`
  ADD CONSTRAINT `fk_project_resolution` FOREIGN KEY (`project`) REFERENCES `projects` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
