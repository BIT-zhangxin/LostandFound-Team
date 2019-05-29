USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_update_user_information`;

delimiter //
CREATE PROCEDURE `proc_update_user_information`(
	IN `id` INT,
	IN `username` VARCHAR(30),
	IN `contact_information` VARCHAR(100),
	IN `introduction` VARCHAR(100)
)
BEGIN
	if(`username`!='') then
		update `user`
		set `user`.username=username
		where `user`.id=id;
	end if;
	if(`contact_information`!='') then
		update `user`
		set `user`.contact_information=contact_information
		where `user`.id=id;
	end if;
    if(`introduction`!='') then
        update `user`
        set `user`.introduction=introduction
        where `user`.id=id;
    end if;
END // #已添加数据库，改动完成