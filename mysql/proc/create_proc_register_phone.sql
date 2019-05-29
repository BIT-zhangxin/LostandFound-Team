USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_register_phone`(
	IN `phone_number` CHAR(11),
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
    where `user`.`phone_number`=`phone_number`);
    if (tmp>0) then
        SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = "手机已被注册";
    end if;
    insert into `user`(`phone_number`,`password`,username,`security_question`,`security_answer`)
    values(`phone_number`,`password`,'',`security_question`,`security_answer`);
    set tmp=last_insert_id();
    update `user`
    set `user`.username=concat('用户',tmp)
    where `user`.id=tmp;

END //