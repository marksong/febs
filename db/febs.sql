/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : febs

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-06-01 22:07:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` varchar(32) NOT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `deleter` varchar(32) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` bit(1) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role
-- ----------------------------

-- ----------------------------
-- Table structure for `auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` varchar(32) NOT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `deleter` varchar(32) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` bit(1) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES ('15a147f3cde74b8d8a3ff027a4354e13', 'test', '2013-05-25 15:04:06', null, null, null, null, '', 'name1', 'pass1', null, null);
INSERT INTO `auth_user` VALUES ('27a020ddf5b2423dade99986df641194', 'test', '2013-05-25 19:20:04', null, null, null, null, '', 'name1', 'pass1', null, null);
INSERT INTO `auth_user` VALUES ('aa432102dabb42cba30412ac30b5a3bf', null, null, null, null, null, null, '', 'name1', 'pass1', null, null);
INSERT INTO `auth_user` VALUES ('bed1d9d190cd4ca4a9f341ffe930e86d', 'test', '2013-05-25 19:26:55', null, null, null, null, '', 'name1', 'pass1', null, null);
INSERT INTO `auth_user` VALUES ('e3c06e39a45548b4b3c583c1f24a093b', 'test', '2013-05-25 19:20:13', null, null, null, null, '', 'name1', 'pass1', null, null);

-- ----------------------------
-- Table structure for `auth_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `id` varchar(32) NOT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `deleter` varchar(32) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` bit(1) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for `auth_auth`
-- ----------------------------
DROP TABLE IF EXISTS `auth_auth`;
CREATE TABLE `auth_auth` (
  `id` varchar(32) NOT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `deleter` varchar(32) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` bit(1) DEFAULT NULL,
  `auth_code` varchar(32) DEFAULT NULL,
  `auth_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_auth
-- ----------------------------

-- ----------------------------
-- Table structure for `auth_role_auth`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_auth`;
CREATE TABLE `auth_role_auth` (
  `id` varchar(32) NOT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `deleter` varchar(32) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_flag` bit(1) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `auth_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role_auth
-- ----------------------------