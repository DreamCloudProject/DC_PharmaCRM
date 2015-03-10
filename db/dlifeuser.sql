--
-- Пользователь: `dlifeuser`
--
CREATE USER `dlifeuser`@`localhost` IDENTIFIED BY "AnotherLifeDBUser";

GRANT ALL PRIVILEGES ON another_life_db.* TO `dlifeuser`@`localhost`;

FLUSH PRIVILEGES;

DROP USER `dlifeuser`@`localhost`;