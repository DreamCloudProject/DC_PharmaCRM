-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Фев 11 2015 г., 00:06
-- Версия сервера: 5.5.25
-- Версия PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `another_life_db`
--
CREATE DATABASE `another_life_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `another_life_db`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Дамп данных таблицы `common_roles`
--

INSERT INTO `common_roles` (`role_id`, `title`, `description`, `access_level`) VALUES
(1, 'Владелец компании', 'Роль с правами администратора системы.', 'OWNER'),
(2, 'Сотрудник компании', 'Роль уполномоченного пользователя.', 'EMPLOYEE'),
(3, 'Пользователь', 'Роль с правами продвинутого пользователя.', 'USER'),
(4, 'Гость', 'Роль для базовых действий в приложении.', 'GUEST');

-- --------------------------------------------------------

--
-- Структура таблицы `common_rules`
--

CREATE TABLE IF NOT EXISTS `common_rules` (
  `rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `component_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `common_rules`
--

INSERT INTO `common_rules` (`rule_id`, `component_name`) VALUES
(1, 'AdminDisabled'),
(2, 'CreateDisabled'),
(3, 'EditDisabled'),
(4, 'DeleteDisabled'),
(5, 'ViewDocumentsDisabled'),
(6, 'ViewContactInfoDisabled');

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

--
-- Дамп данных таблицы `common_roles_rules_rel`
--

INSERT INTO `common_roles_rules_rel` (`role`, `rule`) VALUES
(2, 1),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6);

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

--
-- Дамп данных таблицы `common_user_info`
--

INSERT INTO `common_user_info` (`user_info_id`, `login`, `password`, `firstname`, `middlename`, `lastname`, `sessionid`, `role`, `contact_info`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Иван', 'Иванович', 'Иванов', 'FA77EA969E2D1F5C9E99E857069532D5', 1, NULL);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Дамп данных таблицы `diagnosis`
--

INSERT INTO `diagnosis` (`diagnosis_id`, `title`, `description`) VALUES
(1, 'ПНГ', 'Пароксизмальная ночная гемоглобинурия.'),
(2, 'аГУС', 'Атипичный гемолитико-уремический синдром.');

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

--
-- Дамп данных таблицы `districts`
--

INSERT INTO `districts` (`district_id`, `title`, `description`) VALUES
(1, 'Центральный федеральный округ', 'Административное формирование в западной части России. Образован указом президента РФ от 13 мая 2000 года. Представлен областями и городом федерального значения.'),
(2, 'Южный федеральный округ', 'Административное формирование на юге европейской части России. Образован указом президента РФ от 13 мая 2000 года.'),
(3, 'Северо-Западный федеральный округ', 'Административное формирование на севере и северо-западе европейской части России. Включает в свой состав следующие субъекты Российской Федерации'),
(4, 'Дальневосточный федеральный округ', 'Административное формирование на Дальнем Востоке России. Образован указом Президента Российской Федерации от 13 мая 2000 года с базированием Администрации ДФО в г. Хабаровске. В округе практически все субъекты имеют выход к морю (кроме Амурской области и Еврейской автономной области).'),
(5, 'Сибирский федеральный округ', 'Административное формирование в сибирской части России. Образован указом президента РФ от 13 мая 2000 года.'),
(6, 'Уральский федеральный округ', 'Административное формирование в России, в пределах Урала и Западной Сибири. Образован указом президента России от 13 мая 2000 года.'),
(7, 'Приволжский федеральный округ', 'Административное формирование Российской Федерации, образованное указом президента РФ от 13 мая 2000 года. В состав округа входят 14 субъектов федерации. '),
(8, 'Северо-Кавказский федеральный округ', 'Федеральный округ Российской Федерации, выделенный из состава Южного федерального округа указом президента России Д. А. Медведева от 19 января 2010 года. Расположен на юге европейской части России, в центральной и восточной части Северного Кавказа.'),
(9, 'Крымский федеральный округ', 'Федеральный округ Российской Федерации, образованный указом президента России В. В. Путина от 21 марта 2014 года.');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Дамп данных таблицы `event_reasons`
--

INSERT INTO `event_reasons` (`event_reason_id`, `title`, `description`) VALUES
(1, 'Предоставление ИС из лаборатории', NULL),
(2, 'Оформление направления на обследование в ФЦ', NULL),
(3, 'Получение направления на обследование в ФЦ', NULL),
(4, 'Получение выписки/консультативного заключения/решения консилиума из ФЦ', NULL),
(5, 'Обращение на проведение ВК главврачу', NULL),
(6, 'Обращение на проведение ВК в МЗ', NULL),
(7, 'Обращение на внесение в регистр в МЗ', NULL),
(8, 'Обращение на внесение в регистр главврачу', NULL),
(9, 'Обращение на внесение в регистр главному специалисту', NULL),
(10, 'Обращение на проведение ВК главному специалисту', NULL),
(11, 'Обращение  на обеспечение таргетной терапией в МЗ', NULL),
(12, 'Обращение на выписку рецепта главному врачу', NULL),
(13, 'Обращение к УППЧ/УППР региона', NULL),
(14, 'Обращение к УППЧ/УППР РФ', NULL),
(15, 'Обращение к Президенту РФ', NULL),
(16, 'Обращение к Председателю Правительства', NULL),
(17, 'Обращение к губернатору/главе Республики', NULL),
(18, 'Обращение к вице-губернатору по соц вопросам', NULL),
(19, 'Обращение в региональный Росздравнадзор', NULL),
(20, 'Обращение в региональное ОФОМС', NULL),
(21, 'Обращение в МЗ РФ', NULL),
(22, 'Обращение в Росздравнадзор РФ', NULL),
(23, 'Обращение в благотворительный фонд', NULL),
(24, 'Обращение к депутату ГД РФ', NULL),
(25, 'Обращение к депутату муниципального образования', NULL),
(26, 'Обращение в Законодательное собрание', NULL),
(27, 'Обращение в МинФин региона', NULL),
(28, 'Обращение в Народный Фронт', NULL),
(29, 'Запись на прием к губернатору/главе Республики', NULL),
(30, 'Запись на прием к вице-губернатору по соц вопросам', NULL),
(31, 'Запись на прием к депутату ГД РФ', NULL),
(32, 'Запись на прием к депутату муниципального образования', NULL),
(33, 'Запись на прием к министру здравоохранения', NULL),
(34, 'Запись на прием к УППЧУППР', NULL);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Дамп данных таблицы `extensions`
--

INSERT INTO `extensions` (`extension_id`, `description`, `extension_name`, `icon_name`, `title`) VALUES
(1, 'Adobe Acrobat Reader Document', 'PDF', 'fa-file-pdf-o', 'Документ PDF'),
(2, 'Microsoft Office Excel Stylesheet Document', 'XLSX', 'fa-file-excel-o', 'Документ XLSX'),
(3, 'Microsoft Office Document', 'DOCX', 'fa-file-word-o', 'Документ DOCX'),
(4, 'Microsoft Office Excel Stylesheet Document', 'XLS', 'fa-file-excel-o', 'Документ XLS'),
(5, 'Microsoft Office Document', 'DOC', 'fa-file-word-o', 'Документ DOC'),
(6, 'JPEG Image File', 'JPEG', 'fa-file-image-o', 'Документ JPG'),
(7, 'Text Document', 'TXT', 'fa-file-text', 'Документ TXT'),
(8, 'Portable Network Graphics', 'PNG', 'fa-file-image-o', 'Документ PNG'),
(9, 'Неизвестный тип документа', 'UNDEFINED', NULL, 'Документ UNDEFINED');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Дамп данных таблицы `projects`
--

INSERT INTO `projects` (`project_id`, `title`, `description`) VALUES
(1, 'Основной', 'Основной тип проекта'),
(2, 'Юридический', 'Юридический тип проекта'),
(3, 'Лечение', 'Тип проекта лечение'),
(4, 'Наблюдение', 'Наблюдение');

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

--
-- Дамп данных таблицы `regions`
--

INSERT INTO `regions` (`region_id`, `district`, `title`, `description`) VALUES
(1, NULL, 'Агинский Бурятский АО', 'Агинский Бурятский АО'),
(2, 2, 'Адыгея Респ', 'Адыгея Респ'),
(3, 5, 'Алтай Респ', 'Алтай Респ'),
(4, NULL, 'Алтай Респ', 'Алтай Респ'),
(5, 5, 'Алтайский край', 'Алтайский край'),
(6, 4, 'Амурская обл', 'Амурская обл'),
(7, 3, 'Архангельская обл', 'Архангельская обл'),
(8, 2, 'Астраханская обл', 'Астраханская обл'),
(9, NULL, 'Байконур г', 'Байконур г'),
(10, 7, 'Башкортостан Респ', 'Башкортостан Респ'),
(11, 1, 'Белгородская обл', 'Белгородская обл'),
(12, 1, 'Брянская обл', 'Брянская обл'),
(13, 5, 'Бурятия Респ', 'Бурятия Респ'),
(14, 1, 'Владимирская обл', 'Владимирская обл'),
(15, 2, 'Волгоградская обл', 'Волгоградская обл'),
(16, 3, 'Вологодская обл', 'Вологодская обл'),
(17, 1, 'Воронежская обл', 'Воронежская обл'),
(18, NULL, 'Горьковская обл', 'Горьковская обл'),
(19, 8, 'Дагестан Респ', 'Дагестан Респ'),
(20, 4, 'Еврейская Аобл', 'Еврейская Аобл'),
(21, 5, 'Забайкальский край', 'Забайкальский край'),
(22, NULL, 'Забайкальский край Агинский Бурятский округ', 'Забайкальский край Агинский Бурятский округ'),
(23, 1, 'Ивановская обл', 'Ивановская обл'),
(24, 8, 'Ингушетия Респ', 'Ингушетия Респ'),
(25, 5, 'Иркутская обл', 'Иркутская обл'),
(26, NULL, 'Иркутская обл Усть-Ордынский Бурятский округ', 'Иркутская обл Усть-Ордынский Бурятский округ'),
(27, 8, 'Кабардино-Балкарская Респ', 'Кабардино-Балкарская Респ'),
(28, 3, 'Калининградская обл', 'Калининградская обл'),
(29, 2, 'Калмыкия Респ', 'Калмыкия Респ'),
(30, 1, 'Калужская обл', 'Калужская обл'),
(31, NULL, 'Камчатская обл', 'Камчатская обл'),
(32, 4, 'Камчатский край', 'Камчатский край'),
(33, 8, 'Карачаево-Черкесская Респ', 'Карачаево-Черкесская Респ'),
(34, 3, 'Карелия Респ', 'Карелия Респ'),
(35, 5, 'Кемеровская обл', 'Кемеровская обл'),
(36, 7, 'Кировская обл', 'Кировская обл'),
(37, 3, 'Коми Респ', 'Коми Респ'),
(38, NULL, 'Коми-Пермяцкий АО', 'Коми-Пермяцкий АО'),
(39, NULL, 'Корякский АО', 'Корякский АО'),
(40, 1, 'Костромская обл', 'Костромская обл'),
(41, 2, 'Краснодарский край', 'Краснодарский край'),
(42, 5, 'Красноярский край', 'Красноярский край'),
(43, 9, 'Крым Респ', 'Крым Респ'),
(44, 6, 'Курганская обл', 'Курганская обл'),
(45, 1, 'Курская обл', 'Курская обл'),
(46, NULL, 'Ленинград г', 'Ленинград г'),
(47, 3, 'Ленинградская обл', 'Ленинградская обл'),
(48, 1, 'Липецкая обл', 'Липецкая обл'),
(49, 4, 'Магаданская обл', 'Магаданская обл'),
(50, 7, 'Марий Эл Респ', 'Марий Эл Респ'),
(51, 7, 'Мордовия Респ', 'Мордовия Респ'),
(52, 1, 'Москва г', 'Москва г'),
(53, 1, 'Московская обл', 'Московская обл'),
(54, 3, 'Мурманская обл', 'Мурманская обл'),
(55, 3, 'Ненецкий АО', 'Ненецкий АО'),
(56, 7, 'Нижегородская обл', 'Нижегородская обл'),
(57, 3, 'Новгородская обл', 'Новгородская обл'),
(58, 5, 'Новосибирская обл', 'Новосибирская обл'),
(59, 5, 'Омская обл', 'Омская обл'),
(60, 7, 'Оренбургская обл', 'Оренбургская обл'),
(61, 1, 'Орловская обл', 'Орловская обл'),
(62, 7, 'Пензенская обл', 'Пензенская обл'),
(63, 7, 'Пермская обл', 'Пермская обл'),
(64, NULL, 'Пермский край', 'Пермский край'),
(65, 4, 'Приморский край', 'Приморский край'),
(66, 3, 'Псковская обл', 'Псковская обл'),
(67, 2, 'Ростовская обл', 'Ростовская обл'),
(68, 1, 'Рязанская обл', 'Рязанская обл'),
(69, 7, 'Самарская обл', 'Самарская обл'),
(70, 3, 'Санкт-Петербург г', 'Санкт-Петербург г'),
(71, 7, 'Саратовская обл', 'Саратовская обл'),
(72, 4, 'Саха /Якутия/ Респ', 'Саха /Якутия/ Респ'),
(73, 4, 'Сахалинская обл', 'Сахалинская обл'),
(74, 6, 'Свердловская обл', 'Свердловская обл'),
(75, 9, 'Севастополь г', 'Севастополь г'),
(76, 8, 'Северная Осетия - Алания Респ', 'Северная Осетия - Алания Респ'),
(77, 1, 'Смоленская обл', 'Смоленская обл'),
(78, 8, 'Ставропольский край', 'Ставропольский край'),
(79, NULL, 'Таймырский (Долгано-Ненецкий) АО', 'Таймырский (Долгано-Ненецкий) АО'),
(80, 1, 'Тамбовская обл', 'Тамбовская обл'),
(81, 7, 'Татарстан Респ', 'Татарстан Респ'),
(82, 1, 'Тверская обл', 'Тверская обл'),
(83, 5, 'Томская обл', 'Томская обл'),
(84, 1, 'Тульская обл', 'Тульская обл'),
(85, 5, 'Тыва Респ', 'Тыва Респ'),
(86, 6, 'Тюменская обл', 'Тюменская обл'),
(87, 7, 'Удмуртская Респ', 'Удмуртская Респ'),
(88, 7, 'Ульяновская обл', 'Ульяновская обл'),
(89, NULL, 'Усть-Ордынский Бурятский АО', 'Усть-Ордынский Бурятский АО'),
(90, 4, 'Хабаровский край', 'Хабаровский край'),
(91, 5, 'Хакасия Респ', 'Хакасия Респ'),
(92, 6, 'Ханты-Мансийский АО', 'Ханты-Мансийский АО'),
(93, NULL, 'Ханты-Мансийский Автономный округ - Югра АО', 'Ханты-Мансийский Автономный округ - Югра АО'),
(94, 6, 'Челябинская обл', 'Челябинская обл'),
(95, 8, 'Чеченская Респ', 'Чеченская Респ'),
(96, NULL, 'Читинская обл', 'Читинская обл'),
(97, 7, 'Чувашская - Чувашия Респ', 'Чувашская - Чувашия Респ'),
(98, NULL, 'Чувашская Республика - Чувашия', 'Чувашская Республика - Чувашия'),
(99, 4, 'Чукотский АО', 'Чукотский АО'),
(100, NULL, 'Эвенкийский АО', 'Эвенкийский АО'),
(101, 6, 'Ямало-Ненецкий АО', 'Ямало-Ненецкий АО'),
(102, 1, 'Ярославская обл', 'Ярославская обл');

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
-- Дамп данных таблицы `resolutions`
--

INSERT INTO `resolutions` (`resolution_id`, `title`, `description`, `project`) VALUES
(1, 'Новый пациент', 'Пациент введен. Поставлен на учет.', 1),
(2, 'Пациент под наблюдением ПО', 'Пациент под наблюдением.', 1),
(3, 'Получение рекомендаций', 'Случай открыт.', 1),
(4, 'Подача обращений', 'Случай открыт - запрос на финансирование подан.', 1),
(5, 'Одобрено', 'Случай открыт - финансирование утверждено.', 1),
(6, 'Пациент на терапии', 'Пациент проходящий лечение.', 1),
(7, 'Выигранные суды, идут закупки', 'Выигранные суды, идут закупки', 2),
(8, 'Выигранные суды к исполнению', 'Выигранные суды к исполнению', 2),
(9, 'Апелляции', 'Апелляции', 2),
(10, 'Суды в ходу', 'Суды в ходу', 2),
(11, 'Поданные иски', 'Поданные иски', 2),
(12, 'В работе иски к подаче', 'В работе иски к подаче', 2),
(13, 'Подготовка', 'Подготовка', 2),
(14, 'Замороженные', 'Замороженные', 2);

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
-- Ограничения внешнего ключа таблицы `patients`
--
ALTER TABLE `patients`
  ADD CONSTRAINT `fk_contact_info_patient` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_diagnosis` FOREIGN KEY (`diagnosis`) REFERENCES `diagnosis` (`diagnosis_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
