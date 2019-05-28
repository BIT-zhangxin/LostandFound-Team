USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_download_profile_photo`;

delimiter //
CREATE PROCEDURE `proc_download_profile_photo` ( IN `id` INT ) BEGIN
	SELECT
		`user`.`profile_photo_format`,
		`user`.`profile_photo`
	FROM
		`LostandFound`.`user`
	WHERE
		`user`.`id` = `id`;

END //

#未添加数据库，待改动