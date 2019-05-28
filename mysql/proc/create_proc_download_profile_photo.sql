USE `LostandFound`;
delimiter //
CREATE PROCEDURE `proc_download_profile_photo`(
  IN `id` INT
)
BEGIN
  select `user`.`profile_photo_format`,`user`.`profile_photo`
  from `LostandFound`.`user`
  where `user`.`id`=`id`;
END //
delimiter ;

#未添加数据库，待改动