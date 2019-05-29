USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_apply`;

delimiter //
CREATE PROCEDURE `proc_apply` (
	IN `main_event_id` INT,
	IN `event_type` INT,
	IN `origin_user_id` INT,
	IN `aim_user_id` INT,
	IN `description` VARCHAR ( 100 )
) BEGIN
	INSERT INTO `LostandFound`.`sub_event` ( `main_event_id`, `event_type`, `origin_user_id`,`aim_user_id`,`description`,`time` )
	VALUES
		( `main_event_id`, `event_type`, `origin_user_id`,`aim_user_id`,`description`,NOW() );

END // #添加数据库，改动完成