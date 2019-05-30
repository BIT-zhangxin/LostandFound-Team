USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_apply`;

delimiter //
CREATE PROCEDURE `proc_apply` ( IN `main_event_id` INT, IN `event_type` INT, IN `origin_user_id` INT, IN `aim_user_id` INT, IN `description` VARCHAR ( 100 ) ) BEGIN
	DECLARE
		tmp INT;

	SET tmp = (
		SELECT
			count( 1 )
		FROM
			`LostandFound`.`sub_event`
		WHERE
			`sub_event`.`origin_user_id` = `origin_user_id`
			AND `sub_event`.`main_event_id` = `main_event_id`
			AND `sub_event`.`event_type` = `event_type`
		);
	IF
		( tmp > 0 ) THEN
			SIGNAL SQLSTATE 'HY000'
			SET MESSAGE_TEXT = "self";

	END IF;
	INSERT INTO `LostandFound`.`sub_event` ( `main_event_id`, `event_type`, `origin_user_id`, `aim_user_id`, `description`, `time` )
	VALUES
		( `main_event_id`, `event_type`, `origin_user_id`, `aim_user_id`, `description`, NOW( ) );

END // #添加数据库，改动完成