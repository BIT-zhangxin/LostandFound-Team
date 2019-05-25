USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_upload_object_picture`(
  IN `id` INT,
  IN `format` VARCHAR(10),
  IN `object_picture` BLOB
)
BEGIN
  update `LostandFound`.`object` set
  `object`.`picture_format`=`format`,
  `object`.`picture`=`object_picture`
  where `object`.`id`=`id`;
END //
delimiter ;

#未添加数据库，待改动