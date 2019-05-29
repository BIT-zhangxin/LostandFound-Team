USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_select_id_by_phone_number`;

delimiter //
CREATE PROCEDURE `proc_select_id_by_phone_number` ( IN `phone_number` CHAR ( 11 ) ) BEGIN
	SELECT
		`user`.`id`,
		`user`.`security_question`
	FROM
		`LostandFound`.`user`
	WHERE
		`user`.`phone_number` = `phone_number`;

END // #已添加数据库，改动完成