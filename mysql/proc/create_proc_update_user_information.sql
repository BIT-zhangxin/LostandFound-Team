USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_user_information`(
	IN `id` INT,
	IN `username` VARCHAR(30),
	IN `contact_information` VARCHAR(100),
	IN `introduction` VARCHAR(100)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
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
END //