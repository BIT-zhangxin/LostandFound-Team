USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_password_question`(
	IN `id` INT,
	IN `security_answer` VARCHAR(100),
	IN `new_password` CHAR(32)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	declare tmp int;
	set tmp=(select count(1) from `user` where `user`.id=id
	and `user`.`security_answer`=`security_answer`);
	if (tmp=0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "密保答案错误";
	end if;
	update `user`
	set `user`.`password`=`new_password`
	where `user`.id=id;
END //
