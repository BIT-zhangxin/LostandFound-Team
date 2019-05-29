USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_download_profile_photo`;

delimiter //
CREATE PROCEDURE `proc_download_profile_photo` ( IN `id` INT ) BEGIN
	SELECT
		`user`.`profile_photo_format` AS `format`,
		`user`.`profile_photo`
	FROM
		`LostandFound`.`user`
	WHERE
		`user`.`id` = `id`;

END // #已添加数据库，改动完成