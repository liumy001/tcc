/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50528
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2015-07-30 16:46:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('269341969fcb41a4a92c5992f544e3ca', 'eric1', '1234567', null, null, null, null);
INSERT INTO `t_user` VALUES ('43cd70714cb64c898ccc8338de11bf50', 'eric2', '123456', null, null, null, null);
INSERT INTO `t_user` VALUES ('4ba44da3a6e7419197323bd653e1be57', 'eric3', '123456', null, null, null, null);
INSERT INTO `t_user` VALUES ('82b88853ce85486bba73b0ef66c106e5', 'eric4', '123456', null, null, null, null);
INSERT INTO `t_user` VALUES ('9b1b574043dc41dcb572190378bddbb7', 'eric5', '123456', null, null, null, null);
INSERT INTO `t_user` VALUES ('ead7b4c3331e442984a36b3fc7c78bc0', 'eric6', '123456', null, null, null, null);
