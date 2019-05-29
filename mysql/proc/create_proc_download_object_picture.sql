USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_download_object_picture`;

delimiter //
CREATE PROCEDURE `proc_download_object_picture` ( IN `id` INT ) BEGIN
	SELECT
		`object`.`picture_format`,
		`object`.`picture`
	FROM
		`LostandFound`.`object`
	WHERE
		`object`.`id` = `id`;

END // #已添加数据库，改动完成