ALTER TABLE `different_life_db`.`attendant_persons` ADD COLUMN `commentary` VARCHAR(1024) NULL  AFTER `contact_info` ;

ALTER TABLE `different_life_db`.`events` ADD COLUMN `event_number` INT(11) NULL  AFTER `posted_by_user` ;

ALTER TABLE `different_life_db`.`patient_histories` ADD COLUMN `event_current_count` INT(11) NULL  AFTER `status` ;

UPDATE `different_life_db`.`patient_histories` SET `event_current_count` =  1;

DROP procedure IF EXISTS `different_life_db`.`update_events`;

DELIMITER $$

USE `different_life_db`$$

CREATE DEFINER=`dlifeuser`@`localhost` PROCEDURE `update_events`()

BEGIN

    DECLARE done BOOLEAN;

    DECLARE ev_num INT DEFAULT 1;  

    DECLARE current_ph_num INT;

    DECLARE current_ev_id INT;

    DECLARE previous_ph_num INT DEFAULT 0;

    DECLARE events_cur cursor for select `t`.`event_id`,`t`.`patient_history` from `different_life_db`.`events` as `t` order by `t`.`patient_history` asc; 

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN events_cur;

	events_loop: LOOP 

		FETCH events_cur INTO current_ev_id,current_ph_num;

		IF done THEN

			CLOSE events_cur;

			LEAVE events_loop;

		END IF;

        IF (current_ph_num <> previous_ph_num) THEN

            SET ev_num = 1;

        END IF;

		IF (current_ph_num = previous_ph_num) THEN 

			SET ev_num = ev_num + 1;

        END IF;

        UPDATE `different_life_db`.`events` SET `event_number` =  ev_num where `patient_history`=current_ph_num and `event_id`=current_ev_id;

        UPDATE `different_life_db`.`patient_histories` SET `event_current_count` =  ev_num where `patient_histories_id`=current_ph_num;

        SET previous_ph_num = current_ph_num;		

   END LOOP events_loop;

END$$

DELIMITER ;

CALL `different_life_db`.`update_events`;

DROP procedure IF EXISTS `different_life_db`.`update_events`;