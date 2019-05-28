USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_getID`(
	IN `id_or_phone_number` VARCHAR(20)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	if char_length(id_or_phone_number)=11 then
		select id,security_question from `user` where
		phone_number=id_or_phone_number;
	else
		select id,security_question from `user` where
		id=id_or_phone_number;
	end if;
END //