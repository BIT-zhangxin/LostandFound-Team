USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_register_phone`;

delimiter //
CREATE DEFINER = `root` @`localhost` PROCEDURE `proc_register_phone` (
	IN `phone_number` CHAR ( 11 ),
	IN `password` CHAR ( 32 ),
	IN `security_question` VARCHAR ( 100 ),
	IN `security_answer` VARCHAR ( 100 )
	) BEGIN
	DECLARE
		tmp INT;

	SET tmp = ( SELECT count( 1 ) FROM `user` WHERE `user`.`phone_number` = `phone_number` );
	IF
		( tmp > 0 ) THEN
			SIGNAL SQLSTATE 'HY000'
			SET MESSAGE_TEXT = "手机已被注册";

	END IF;
	INSERT INTO `user` ( `phone_number`, `password`, username, `security_question`, `security_answer`, `contact_information` )
	VALUES
		( `phone_number`, `password`, '', `security_question`, `security_answer`, '该用户未填写联系方式' );

	SET tmp = last_insert_id( );
	UPDATE `user`
	SET `user`.username = concat( '用户', tmp )
	WHERE
		`user`.id = tmp;

END // #已添加数据库，改动完成