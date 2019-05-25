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

 Date: 25/05/2019 12:54:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for main_event
-- ----------------------------
DROP TABLE IF EXISTS `main_event`;
CREATE TABLE `main_event`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_type` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `object_id` int(11) NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
