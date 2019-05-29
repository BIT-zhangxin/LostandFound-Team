USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_select_userinfo`;

delimiter //
CREATE PROCEDURE `proc_select_userinfo` ( IN `id` INT ) BEGIN
	SELECT
		`user`.`username` AS `username`,
		`user`.`phone_number` AS `phone_number`,
		`user`.`email_address` AS `email_address`,
		`user`.`contact_information` AS `contact_information`,
		`user`.`introduction` AS `introduction`,
		`user`.`credit_score` AS `credit_score`
	FROM
		`LostandFound`.`user`
	WHERE
		`user`.`id` = `id`;

END // #已添加数据库，待改动