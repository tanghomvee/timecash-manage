CREATE SCHEMA `timecash` ;

use timecash;

CREATE TABLE `timecash`.`t_apply_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NULL,
  `phone_num` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `addr` VARCHAR(100) NULL,
  `remark` VARCHAR(500) NULL,
  `create_time` DATETIME NULL,
  PRIMARY KEY (`id`)
);