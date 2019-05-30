USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_apply_reply`;

delimiter //
CREATE PROCEDURE `proc_apply_reply` ( IN `sub_event_id` INT, IN `event_type` INT ) BEGIN
	UPDATE `LostandFound`.`sub_event`
	SET `sub_event`.`event_type` = `event_type`,
	`sub_event`.`time` = NOW( )
	WHERE
		`sub_event`.`id` = `sub_event_id`;
	IF
		( `event_type` = 3 OR `event_type` = 8 ) THEN
			UPDATE `main_event`
			SET `main_event`.`event_type` = `main_event`.`event_type` + 10
			WHERE
			`main_event`.`id` = (
				SELECT `sub_event`.`main_event_id` FROM `LostandFound`.`sub_event` WHERE `sub_event`.`id` = `sub_event_id` LIMIT 1
			);

	END IF;

END // #添加数据库，改动完成