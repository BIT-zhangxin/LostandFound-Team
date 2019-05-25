USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_select_message`;

delimiter //
CREATE PROCEDURE `proc_select_message` ( ) BEGIN
	SELECT
		`main_event`.id AS `main_event_id`,
		`main_event`.event_type AS `main_event_type`,
		`main_event`.`object_id` AS `object_id`,
		`object`.`name` AS `name`,
		`object`.location AS `location`,
		`object`.`time` AS `time`,
		`object`.description AS `description`
	FROM
		`LostandFound`.`object`,
		`LostandFound`.`main_event`
	WHERE
		`main_event`.`object_id` = `object`.id
		AND `main_event`.event_type < 10;

END //

#已添加数据库，待改动