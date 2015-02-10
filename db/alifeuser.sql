--
-- Пользователь: `alifeuser`
--
CREATE USER `alifeuser`@`localhost` IDENTIFIED BY "AnotherLifeDBUser";

GRANT ALL PRIVILEGES ON another_life_db.* TO `alifeuser`@`localhost`;

FLUSH PRIVILEGES;

DROP USER `alifeuser`@`localhost`;