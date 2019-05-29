USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_update_password_phone`;

delimiter //
CREATE PROCEDURE `proc_update_password_phone`(
	IN `id` INT,
	IN `old_password` CHAR(32),
	IN `new_phone` CHAR(11)
)
BEGIN
	declare tmp int;
	set tmp=(select count(1) from `user` where `user`.id=id and `user`.`password`=`old_password`);
	if (tmp=0) then
		SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "密码错误";
	end if;
	update `user`
	set `user`.`phone_number`=`new_phone`
	where `user`.id=id;
END // #已添加数据库，改动完成
