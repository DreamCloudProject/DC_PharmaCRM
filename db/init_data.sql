INSERT INTO `common_roles` (`role_id`, `title`, `description`, `access_level`) VALUES
(1, 'Владелец компании', 'Роль с правами администратора системы.', 'OWNER'),
(2, 'Сотрудник компании', 'Роль уполномоченного пользователя.', 'EMPLOYEE'),
(3, 'Пользователь', 'Роль с правами продвинутого пользователя.', 'USER'),
(4, 'Гость', 'Роль для базовых действий в приложении.', 'GUEST');

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

--
-- Дамп данных таблицы `common_roles_rules_rel`
--

INSERT INTO `common_roles_rules_rel` (`role`, `rule`) VALUES
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(2, 1),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6);

--
-- Дамп данных таблицы `common_user_info`
--

INSERT INTO `common_user_info` (`user_info_id`, `login`, `password`, `firstname`, `middlename`, `lastname`, `sessionid`, `role`, `contact_info`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Иван', 'Иванович', 'Иванов', '8022E342707E73CA42D56D49366E9FF5', 1, null);

--
-- Дамп данных таблицы `diagnosis`
--

INSERT INTO `diagnosis` (`diagnosis_id`, `title`, `description`) VALUES
(1, 'ПНГ', 'Пароксизмальная ночная гемоглобинурия.'),
(2, 'аГУС', 'Атипичный гемолитико-уремический синдром.');

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

--
-- Дамп данных таблицы `event_reasons`
--

INSERT INTO `event_reasons` (`event_reason_id`, `title`, `description`) VALUES
(1, 'Предоставление ИС из лаборатории', null),
(2, 'Оформление направления на обследование в ФЦ', null),
(3, 'Получение направления на обследование в ФЦ', null),
(4, 'Получение выписки/консультативного заключения/решения консилиума из ФЦ', null),
(5, 'Обращение на проведение ВК главврачу', null),
(6, 'Обращение на проведение ВК в МЗ', null),
(7, 'Обращение на внесение в регистр в МЗ', null),
(8, 'Обращение на внесение в регистр главврачу', null),
(9, 'Обращение на внесение в регистр главному специалисту', null),
(10, 'Обращение на проведение ВК главному специалисту', null),
(11, 'Обращение  на обеспечение таргетной терапией в МЗ', null),
(12, 'Обращение на выписку рецепта главному врачу', null),
(13, 'Обращение к УППЧ/УППР региона', null),
(14, 'Обращение к УППЧ/УППР РФ', null),
(15, 'Обращение к Президенту РФ', null),
(16, 'Обращение к Председателю Правительства', null),
(17, 'Обращение к губернатору/главе Республики', null),
(18, 'Обращение к вице-губернатору по соц вопросам', null),
(19, 'Обращение в региональный Росздравнадзор', null),
(20, 'Обращение в региональное ОФОМС', null),
(21, 'Обращение в МЗ РФ', null),
(22, 'Обращение в Росздравнадзор РФ', null),
(23, 'Обращение в благотворительный фонд', null),
(24, 'Обращение к депутату ГД РФ', null),
(25, 'Обращение к депутату муниципального образования', null),
(26, 'Обращение в Законодательное собрание', null),
(27, 'Обращение в МинФин региона', null),
(28, 'Обращение в Народный Фронт', null),
(29, 'Запись на прием к губернатору/главе Республики', null),
(30, 'Запись на прием к вице-губернатору по соц вопросам', null),
(31, 'Запись на прием к депутату ГД РФ', null),
(32, 'Запись на прием к депутату муниципального образования', null),
(33, 'Запись на прием к министру здравоохранения', null),
(34, 'Запись на прием к УППЧ\УППР', null);

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

--
-- Дамп данных таблицы `projects`
--

INSERT INTO `projects` (`project_id`, `title`, `description`) VALUES
(1, 'Основной', 'Основной тип проекта'),
(2, 'Юридический', 'Юридический тип проекта'),
(3, 'Лечение', 'Тип проекта лечение'),
(4, 'Наблюдение', 'Наблюдение');

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