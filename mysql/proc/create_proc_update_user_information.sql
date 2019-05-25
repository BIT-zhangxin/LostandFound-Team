USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_user_information`(
	IN `id` INT,
	IN `username` VARCHAR(20),
	IN `contact_information` VARCHAR(50),
	IN `personal_profile` VARCHAR(255)
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
    if(`personal_profile`!='') then
        update `user`
        set `user`.personal_profile=personal_profile
        where `user`.id=id;
    end if;
END //