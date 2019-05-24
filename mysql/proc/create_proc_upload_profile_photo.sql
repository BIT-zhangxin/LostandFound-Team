USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_upload_profile_photo`(
  IN `id` INT,
  IN `format` VARCHAR(10),
  IN `profile_photo` BLOB
)
BEGIN
  update `LostandFound`.`user` set
  `user`.`profile_photo_format`=`format`,
  `user`.`profile_photo`=`profile_photo`
  where `user`.`id`=`id`;
END //
delimiter ;