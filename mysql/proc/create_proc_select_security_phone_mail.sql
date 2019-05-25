USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_select_security_phone_mail`(
	IN `id` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	select
	`user`.security_question
	from `user`
	where `user`.id=id;
END //

#未添加数据库，待改动