USE `LostandFound`;
DROP PROCEDURE
IF
	EXISTS `proc_select_apply_info`;

delimiter //
CREATE PROCEDURE `proc_select_apply_info` ( IN `user_id` INT ) BEGIN
	SELECT
		`sub_event`.`id ` AS `sub_event_id`,
		`sub_event`.`main_event_id` AS `main_event_id`,
		`object`.`name` AS `object_name`,
		`sub_event`.`event_type` AS `sub_event_type`,
		`sub_event`.`origin_user_id` AS `origin_user_id`,
		`user1`.`username` AS `origin_user_name`,
		`sub_event`.`aim_user_id` AS `aim_user_id`,
		`user2`.`username` AS `aim_user_name`,
		`sub_event`.`description` AS `description`,
		`sub_event`.`time` AS `time`,
		( CASE `sub_event`.`event_type` WHEN 8 THEN `user1`.`contact_information` ELSE NULL END ) AS `contact_information`
	FROM
		`LostandFound`.`sub_event`,
		`LostandFound`.`object`,
		`LostandFound`.`user` AS `user1`,
		`LostandFound`.`user` AS `user2`
	WHERE
		( `sub_event`.`origin_user_id` = `user_id` OR `sub_event`.`aim_user_id` = `user_id` )
		AND `main_event`.`object_id` = `object.id`
		AND `sub_event`.`origin_user_id` = `user1.id`
		AND `sub_event`.`aim_user_id` = `user2.id`;

END // #已添加数据库，改动完成