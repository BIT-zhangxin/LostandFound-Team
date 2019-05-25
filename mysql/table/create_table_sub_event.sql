/*
 Navicat Premium Data Transfer

 Source Server         : MyECS
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 59.110.213.213:3306
 Source Schema         : LostandFound

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 25/05/2019 12:55:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sub_event
-- ----------------------------
DROP TABLE IF EXISTS `sub_event`;
CREATE TABLE `sub_event`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `main_event_id` int(11) NULL DEFAULT NULL,
  `event_type` int(11) NULL DEFAULT NULL,
  `origin_user_id` int(11) NULL DEFAULT NULL,
  `aim_user_id` int(11) NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
