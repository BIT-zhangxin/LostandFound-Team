USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_publish`;

delimiter //
CREATE PROCEDURE `proc_publish` (
	IN `user_id` INT,
	IN `event_type` INT,
	IN `name` VARCHAR ( 100 ),
	IN `location` VARCHAR ( 100 ),
	IN `time` VARCHAR ( 100 ),
	IN `description` VARCHAR ( 100 ),
	IN `question` VARCHAR ( 100 ),
	IN `picture` MEDIUMBLOB,
	IN `picture_format` VARCHAR ( 10 )
	) BEGIN
	DECLARE
		`object_id` INT;
	INSERT INTO `LostandFound`.`object` ( `name`, `location`, `time`, `description`, `picture`, `picture_format` )
	VALUES
		( `name`, `location`, `time`, `description`, `picture`, `picture_format` );

	SET `object_id` = last_insert_id( );
	INSERT INTO `LostandFound`.`main_event` ( `event_type`, `user_id`, `object_id`, `question`, `time` )
	VALUES
		( `event_type`, `user_id`, `object_id`, `question`, NOW( ) );

END // #已添加数据库，改动完成