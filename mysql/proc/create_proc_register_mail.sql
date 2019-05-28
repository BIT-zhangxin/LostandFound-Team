USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_register_mail`(
	IN `phone_number` VARCHAR(50),
	IN `password` CHAR(32),
	IN `security_question` VARCHAR(100),
	IN `security_answer` VARCHAR(100)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	declare tmp int;
    set tmp=(select count(1) from `user`
    where `user`.`email_address`=`phone_number`);
    if (tmp>0) then
        SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "邮箱号已被注册";
    end if;
    insert into `user`(`email_address`,`password`,username,`security_question`,`security_answer`)
    values(`phone_number`,`password`,'',`security_question`,`security_answer`);
    set tmp=last_insert_id();
    update `user`
    set `user`.username=concat('用户',tmp)
    where `user`.id=tmp;


END //