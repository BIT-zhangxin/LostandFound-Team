USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_select_id_by_email_address`;

delimiter //
CREATE PROCEDURE `proc_select_id_by_email_address` ( IN `email_address` VARCHAR ( 50 ) ) BEGIN
	SELECT
		`user`.`id`,
		`user`.`security_question` 
	FROM
		`LostandFound`.`user` 
	WHERE
		`user`.`email_address` = `email_address`;

END // #已添加数据库，改动完成