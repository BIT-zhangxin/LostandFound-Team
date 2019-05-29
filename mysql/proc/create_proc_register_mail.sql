USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_register_mail`;

delimiter //
CREATE PROCEDURE `proc_register_mail` (
	IN `phone_number` VARCHAR ( 50 ),
	IN `password` CHAR ( 32 ),
	IN `security_question` VARCHAR ( 100 ),
	IN `security_answer` VARCHAR ( 100 )
	) BEGIN
	DECLARE
		tmp INT;

	SET tmp = ( SELECT count( 1 ) FROM `user` WHERE `user`.`email_address` = `phone_number` );
	IF
		( tmp > 0 ) THEN
			SIGNAL SQLSTATE 'HY000'
			SET MESSAGE_TEXT = "邮箱号已被注册";

	END IF;
	INSERT INTO `user` ( `email_address`, `password`, username, `security_question`, `security_answer` )
	VALUES
		( `phone_number`, `password`, '', `security_question`, `security_answer` );

	SET tmp = last_insert_id( );
	UPDATE `user`
	SET `user`.username = concat( '用户', tmp )
	WHERE
		`user`.id = tmp;

END // #已添加数据库，改动完成