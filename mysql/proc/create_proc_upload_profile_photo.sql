USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_upload_profile_photo`;

delimiter //
CREATE PROCEDURE `proc_upload_profile_photo` ( IN `id` INT, IN `format` VARCHAR ( 10 ), IN `profile_photo` BLOB ) BEGIN
	UPDATE `LostandFound`.`user`
	SET `user`.`profile_photo_format` = `format`,
	`user`.`profile_photo` = `profile_photo`
	WHERE
		`user`.`id` = `id`;

END //

#未添加数据库，待改动