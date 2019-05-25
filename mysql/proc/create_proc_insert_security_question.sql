USE `LostandFound`;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_insert_security_question`(
	IN `id` INT,
	IN `question` VARCHAR(50),
	IN `answer` VARCHAR(50)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	insert into `user_security_question`
	(id,security_question,security_answer)
	values(id,question,answer);
END //

#未添加数据库，没用了，待删除