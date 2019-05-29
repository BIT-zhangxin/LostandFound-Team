USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_phone_getID`(
	IN `phone_number` CHAR(11)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
    select id,security_question from `user` where
    `user`.phone_number=phone_number;
END //