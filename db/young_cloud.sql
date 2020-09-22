/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:13306
 Source Schema         : young_cloud

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 22/09/2020 11:03:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for young_dept
-- ----------------------------
DROP TABLE IF EXISTS `young_dept`;
CREATE TABLE `young_dept`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(64) NOT NULL COMMENT '上级部门ID',
  `dept_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `order_num` double(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务状态',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `tenantId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_dept
-- ----------------------------
INSERT INTO `young_dept` VALUES (123, 0, '开发部', 1, '0', '2020-09-22 10:33:12', '2020-09-22 10:33:10', '开发部门', 10000, 10000, 0, '000000', NULL);

-- ----------------------------
-- Table structure for young_menu
-- ----------------------------
DROP TABLE IF EXISTS `young_menu`;
CREATE TABLE `young_menu`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `parent_id` bigint(64) NOT NULL COMMENT '上级菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单/按钮名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应路由path',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应路由组件component',
  `perms` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` double(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '业务状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `tenantId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_menu
-- ----------------------------
INSERT INTO `young_menu` VALUES (1, 0, '系统管理', '/system', 'Layout', NULL, 'el-icon-set-up', '0', 1, '0', '2017-12-27 16:39:07', '2019-07-20 16:19:04', '菜单', 10000, 10000, 0, '000000', NULL);
INSERT INTO `young_menu` VALUES (2, 1, '用户管理', '/system/user', 'febs/system/user/Index', 'user:view', '', '0', 1, '0', '2017-12-27 16:47:13', '2019-01-22 06:45:55', '菜单', 10000, 10000, 0, '000000', NULL);
INSERT INTO `young_menu` VALUES (3, 2, '新增用户', '', '', 'user:add', NULL, '1', NULL, '0', '2017-12-27 17:02:58', NULL, '按钮', 10000, 10000, 0, '000000', NULL);
INSERT INTO `young_menu` VALUES (4, 2, '修改用户', '', '', 'user:update', NULL, '1', NULL, '0', '2017-12-27 17:04:07', NULL, '按钮', 10000, 10000, 0, '000000', NULL);
INSERT INTO `young_menu` VALUES (5, 2, '删除用户', '', '', 'user:delete', NULL, '1', NULL, '0', '2017-12-27 17:04:58', NULL, '按钮', 10000, 10000, 0, '000000', NULL);

-- ----------------------------
-- Table structure for young_role
-- ----------------------------
DROP TABLE IF EXISTS `young_role`;
CREATE TABLE `young_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '业务状态',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `tenantId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_role
-- ----------------------------
INSERT INTO `young_role` VALUES (1, '超级管理员', '0', '2020-09-22 10:22:58', '2020-09-22 10:22:55', '超级管理员角色', 10000, 10000, 0, '000000', NULL);

-- ----------------------------
-- Table structure for young_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `young_role_menu`;
CREATE TABLE `young_role_menu`  (
  `role_id` bigint(64) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(64) NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for young_user
-- ----------------------------
DROP TABLE IF EXISTS `young_user`;
CREATE TABLE `young_user`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近访问时间',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别 0男 1女 2保密',
  `dept_id` bigint(64) NULL DEFAULT NULL COMMENT '部门ID',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '状态 0有效 1锁定',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `tenantId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_user
-- ----------------------------
INSERT INTO `young_user` VALUES (10000, 'wenjiarong', '$2a$10$gzhiUb1ldc1Rf3lka4k/WOoFKKGPepHSzJxzcPSN5/65SzkMdc.SK', '631041490@qq.com', '123456789', '2020-09-22 10:20:47', '0', 123, NULL, '0', '2020-09-22 10:21:24', '2020-09-22 10:21:06', '超级管理员', 10000, 10000, 0, '000000', NULL);

-- ----------------------------
-- Table structure for young_user_role
-- ----------------------------
DROP TABLE IF EXISTS `young_user_role`;
CREATE TABLE `young_user_role`  (
  `user_id` bigint(64) NOT NULL COMMENT '用户ID',
  `role_id` bigint(64) NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_user_role
-- ----------------------------
INSERT INTO `young_user_role` VALUES (10000, 1);

SET FOREIGN_KEY_CHECKS = 1;
