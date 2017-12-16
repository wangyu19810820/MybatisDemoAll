/*
Navicat MySQL Data Transfer

Source Server         : localhost_mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-16 11:33:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(40) NOT NULL COMMENT 'id',
  `c_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `status` tinyint(4) DEFAULT NULL,
  `add_user` varchar(36) DEFAULT NULL,
  `add_dttm` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_upd_dttm` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_upd_user` varchar(36) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色(用户角色)';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '编号',
  `dept_name` varchar(64) DEFAULT NULL COMMENT '部门名称',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '电子邮箱',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别,1为男，0为女',
  `real_name` varchar(64) DEFAULT NULL COMMENT '用户真实名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '1表示可用，0表示不可用',
  `add_dttm` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `add_user` varchar(36) DEFAULT NULL COMMENT '添加人',
  `last_upd_dttm` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
  `last_upd_user` varchar(36) DEFAULT '' COMMENT '最近更新人',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
