USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_download_object_picture`(
  IN `id` INT
)
BEGIN
  select `object`.`picture_format`,`object`.`picture`
  from `LostandFound`.`object`
  where `object`.`id`=`id`;
END //
delimiter ;