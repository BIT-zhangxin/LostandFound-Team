USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_login_phone_number`;

delimiter //
CREATE PROCEDURE `proc_login_phone_number` ( IN `phone_number` CHAR ( 11 ), IN `password` CHAR ( 32 ) ) BEGIN
	SELECT
		`id`
	FROM
		`LostandFound`.`user`
	WHERE
		`user`.`phone_number` = `phone_number`
		AND `user`.`password` = `password`;

END //