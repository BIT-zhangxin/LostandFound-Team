USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_select_userinfo`;

delimiter //
CREATE PROCEDURE `proc_select_userinfo` ( IN `id` INT ) BEGIN
	SELECT
		`user`.`id` AS `id`,
		`user`.`phone_number` AS `phone_number`,
		`user`.`username` AS `username`,
		`user`.`contact_information` AS `contact_information`,
		`user`.`credit_score` AS `credit_score`
	FROM
		`LostandFound`.`user`
	WHERE
		`user`.`id` = `id`;

END //