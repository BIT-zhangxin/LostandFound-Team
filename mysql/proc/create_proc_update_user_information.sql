USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_update_user_information`;

delimiter //
CREATE PROCEDURE `proc_update_user_information` (
	IN `id` INT,
	IN `username` VARCHAR ( 30 ),
	IN `contact_information` VARCHAR ( 100 ),
	IN `introduction` VARCHAR ( 100 ),
	IN `profile_photo` MEDIUMBLOB,
	IN `profile_photo_format` VARCHAR ( 10 )
	) BEGIN
	IF
		( `username` != '' ) THEN
			UPDATE `user`
			SET `user`.`username` = `username`
		WHERE
			`user`.id = id;

	END IF;
	IF
		( `contact_information` != '' ) THEN
			UPDATE `user`
			SET `user`.`contact_information` = `contact_information`
		WHERE
			`user`.`id` = `id`;

	END IF;
	IF
		( `introduction` != '' ) THEN
			UPDATE `user`
			SET `user`.`introduction` = `introduction`
		WHERE
			`user`.`id` = `id`;

	END IF;
	IF
		( `picture` IS NULL ) THEN
			UPDATE `user`
			SET `user`.`profile_photo` = `profile_photo`,
			`user`.`profile_photo_format` = `profile_photo_format`
		WHERE
			`user`.id = `id`;

	END IF;

END // #已添加数据库，改动完成