USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_mail_getID`(
	IN `phone_number` VARCHAR(50)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	select id,security_question from `user` where
	email_address=phone_number;
END //