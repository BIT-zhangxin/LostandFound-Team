USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_login_email_address`;

delimiter //
CREATE PROCEDURE `proc_login_email_address` ( IN `email_address` VARCHAR ( 50 ), IN `password` CHAR ( 32 ) ) BEGIN
	SELECT
		`id`
	FROM
		`LostandFound`.`user`
	WHERE
		`user`.`email_address` = `email_address`
		AND `user`.`password` = `password`;

END //

#已添加数据库，改动完成