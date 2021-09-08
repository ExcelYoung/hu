/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 127.0.0.1:3306
 Source Schema         : milk

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 08/09/2021 14:52:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for milk_type
-- ----------------------------
DROP TABLE IF EXISTS `milk_type`;
CREATE TABLE `milk_type`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `deleted_id` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of milk_type
-- ----------------------------
INSERT INTO `milk_type` VALUES (1, '莱思尔', '50', '2021-07-27 15:38:49', '2021-08-18 15:38:52', 0);
INSERT INTO `milk_type` VALUES (2, '蒙牛', '40', '2021-08-18 15:39:18', '2021-08-21 15:39:21', 0);

SET FOREIGN_KEY_CHECKS = 1;
