USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_select_message`;

delimiter //
CREATE PROCEDURE `proc_select_message` ( ) BEGIN
	SELECT
		`main_event`.`id` AS `main_event_id`,
		`main_event`.`event_type` AS `main_event_type`,
		`main_event`.`user_id` AS `user_id`,
		`user`.`username` AS `user_name`,
		`main_event`.`object_id` AS `object_id`,
		`main_event`.`question` AS `question`,
		`main_event`.`time` AS `date`,
		`object`.`name` AS `name`,
		`object`.`time` AS `time`,
		`object`.location AS `location`,
		`object`.`description` AS `description`

	FROM
		`LostandFound`.`main_event`,
		`LostandFound`.`user`,
		`LostandFound`.`object`
	WHERE
		`main_event`.`object_id` = `object`.`id`
		AND `main_event`.`event_type` < 10
		AND `main_event`.`user_id` = `user`.`id`;

END // #已添加数据库，改动完成