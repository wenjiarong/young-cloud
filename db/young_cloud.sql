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

 Date: 29/03/2021 22:40:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for young_base_template
-- ----------------------------
DROP TABLE IF EXISTS `young_base_template`;
CREATE TABLE `young_base_template`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务状态',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_base_template
-- ----------------------------

-- ----------------------------
-- Table structure for young_dept
-- ----------------------------
DROP TABLE IF EXISTS `young_dept`;
CREATE TABLE `young_dept`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
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
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_dept
-- ----------------------------
INSERT INTO `young_dept` VALUES (123, '000000', 0, '开发部', 1, '0', '2020-09-22 10:33:12', '2020-09-22 10:33:10', '开发部门', 10000, 10000, 0, NULL);

-- ----------------------------
-- Table structure for young_file_info
-- ----------------------------
DROP TABLE IF EXISTS `young_file_info`;
CREATE TABLE `young_file_info`  (
  `id` bigint(64) NOT NULL COMMENT '序列号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '000000' COMMENT '租户编号',
  `file_server_id` bigint(64) NOT NULL COMMENT '文件服务器编号',
  `file_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `file_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `file_size` int(11) NOT NULL COMMENT '文件大小',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注 1: file 2: dir',
  `create_user` bigint(64) NOT NULL COMMENT '创建人',
  `create_dept` bigint(64) NOT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '服务器端创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '服务器端修改时间',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `is_deleted` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否已删除 0:未删除，1:已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件信息管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_file_info
-- ----------------------------

-- ----------------------------
-- Table structure for young_file_server
-- ----------------------------
DROP TABLE IF EXISTS `young_file_server`;
CREATE TABLE `young_file_server`  (
  `id` bigint(20) NOT NULL COMMENT '流水号',
  `IP` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件服务器地址',
  `port` smallint(6) NULL DEFAULT NULL COMMENT '端口 tftp：22 http：80 ftp：21',
  `protocol` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '文件传输协议类型 1: tftp 2: http 3:ftp',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `source_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '传输文件源名称路径',
  `destination_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '传输文件目的名称路径',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_dept` bigint(20) NOT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '服务器端创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '服务器端修改时间',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `is_deleted` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否已删除 0:未删除，1:已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件服务器信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_file_server
-- ----------------------------

-- ----------------------------
-- Table structure for young_login_log
-- ----------------------------
DROP TABLE IF EXISTS `young_login_log`;
CREATE TABLE `young_login_log`  (
  `id` bigint(64) NOT NULL COMMENT '唯一标识',
  `user_id` bigint(64) NULL DEFAULT NULL COMMENT '用户id',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `IP` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `SYSTEM` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录浏览器',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_login_log
-- ----------------------------
INSERT INTO `young_login_log` VALUES (1342382588619108353, 10000, '2020-12-25 16:12:04', '', '127.0.0.1', 'Windows 10', 'Chrome 75');
INSERT INTO `young_login_log` VALUES (1342388953697726465, 10000, '2020-12-25 16:37:22', '', '127.0.0.1', 'Windows 10', 'Chrome 75');
INSERT INTO `young_login_log` VALUES (1342389953011625986, 10000, '2020-12-25 16:41:20', '', '127.0.0.1', 'Windows 10', 'Chrome 75');

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
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_menu
-- ----------------------------
INSERT INTO `young_menu` VALUES (1, 0, '系统管理', '/system', 'Layout', NULL, 'el-icon-set-up', '0', 1, '0', '2017-12-27 16:39:07', '2019-07-20 16:19:04', '菜单', 10000, 10000, 0, NULL);
INSERT INTO `young_menu` VALUES (2, 1, '用户管理', '/system/user', 'young/system/user/Index', 'user:view', '', '0', 1, '0', '2017-12-27 16:47:13', '2019-01-22 06:45:55', '菜单', 10000, 10000, 0, NULL);
INSERT INTO `young_menu` VALUES (3, 2, '新增用户', '', '', 'user:add', NULL, '1', NULL, '0', '2017-12-27 17:02:58', NULL, '按钮', 10000, 10000, 0, NULL);
INSERT INTO `young_menu` VALUES (4, 2, '修改用户', '', '', 'user:update', NULL, '1', NULL, '0', '2017-12-27 17:04:07', NULL, '按钮', 10000, 10000, 0, NULL);
INSERT INTO `young_menu` VALUES (5, 2, '删除用户', '', '', 'user:delete', NULL, '1', NULL, '0', '2017-12-27 17:04:58', NULL, '按钮', 10000, 10000, 0, NULL);

-- ----------------------------
-- Table structure for young_role
-- ----------------------------
DROP TABLE IF EXISTS `young_role`;
CREATE TABLE `young_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `role_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '业务状态',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_role
-- ----------------------------
INSERT INTO `young_role` VALUES (1, '000000', '超级管理员', '0', '2020-09-22 10:22:58', '2020-09-22 10:22:55', '超级管理员角色', 10000, 10000, 0, NULL);

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
INSERT INTO `young_role_menu` VALUES (1, 3);
INSERT INTO `young_role_menu` VALUES (1, 1);
INSERT INTO `young_role_menu` VALUES (1, 2);
INSERT INTO `young_role_menu` VALUES (1, 4);
INSERT INTO `young_role_menu` VALUES (1, 5);

-- ----------------------------
-- Table structure for young_trade_log
-- ----------------------------
DROP TABLE IF EXISTS `young_trade_log`;
CREATE TABLE `young_trade_log`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `goods_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务状态',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_trade_log
-- ----------------------------

-- ----------------------------
-- Table structure for young_transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `young_transaction_log`;
CREATE TABLE `young_transaction_log`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `transaction_Id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事务id',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务状态',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_transaction_log
-- ----------------------------

-- ----------------------------
-- Table structure for young_ureport_file
-- ----------------------------
DROP TABLE IF EXISTS `young_ureport_file`;
CREATE TABLE `young_ureport_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` mediumblob NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_ureport_file
-- ----------------------------
INSERT INTO `young_ureport_file` VALUES (2, '222.ureport.xml', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E3C757265706F72743E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241312220726F773D22312220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242312220726F773D22312220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243312220726F773D22312220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2244312220726F773D22312220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241322220726F773D22322220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242322220726F773D22322220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243322220726F773D22322220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2244322220726F773D22322220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241332220726F773D22332220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242332220726F773D22332220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243332220726F773D22332220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2244332220726F773D22332220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C726F7720726F772D6E756D6265723D223122206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223222206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223322206865696768743D223138222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2231222077696474683D223830222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2232222077696474683D223830222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2233222077696474683D223830222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2234222077696474683D223830222F3E3C706170657220747970653D22413422206C6566742D6D617267696E3D223930222072696768742D6D617267696E3D223930220A20202020746F702D6D617267696E3D2237322220626F74746F6D2D6D617267696E3D2237322220706167696E672D6D6F64653D22666974706167652220666978726F77733D2230220A2020202077696474683D2235393522206865696768743D2238343222206F7269656E746174696F6E3D22706F727472616974222068746D6C2D7265706F72742D616C69676E3D226C656674222062672D696D6167653D22222068746D6C2D696E74657276616C2D726566726573682D76616C75653D22302220636F6C756D6E2D656E61626C65643D2266616C7365223E3C2F70617065723E3C2F757265706F72743E, '2020-11-19 16:55:00', '2020-11-19 16:55:00');

-- ----------------------------
-- Table structure for young_user
-- ----------------------------
DROP TABLE IF EXISTS `young_user`;
CREATE TABLE `young_user`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近访问时间',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别 0男 1女 2保密',
  `dept_id` bigint(64) NULL DEFAULT NULL COMMENT '部门ID',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '状态 0锁定 1有效',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` smallint(2) NULL DEFAULT NULL COMMENT '软删除 0未删除 1删除',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1318470775754153987 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of young_user
-- ----------------------------
INSERT INTO `young_user` VALUES (10000, '000000', 'wenjiarong', '$2a$10$gzhiUb1ldc1Rf3lka4k/WOoFKKGPepHSzJxzcPSN5/65SzkMdc.SK', '温家荣', '631041490@qq.com', '123456789', '2020-12-25 16:41:20', '0', 123, NULL, '1', '2020-09-22 10:21:24', '2020-09-22 10:21:06', '超级管理员', 10000, 10000, 0, NULL);
INSERT INTO `young_user` VALUES (1318470775754153986, '111111', 'wenjia', '$2a$10$DnxSYKmlhiwNThTZdfCAFuBe4UdHjVzZW//iMNIZ0TKmvfvn7w9Gu', '温家', '631041490@qq.com', '9874562121', NULL, '2', NULL, 'default.jpg', '1', NULL, '2020-10-20 16:35:04', NULL, NULL, NULL, NULL, NULL);

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
INSERT INTO `young_user_role` VALUES (1318470775754153986, 1);

-- ----------------------------
-- Table structure for zipkin_annotations
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_annotations`;
CREATE TABLE `zipkin_annotations`  (
  `trace_id_high` bigint(20) NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
  `span_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.id',
  `a_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` blob NULL COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` int(11) NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` bigint(20) NULL DEFAULT NULL COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` int(11) NULL DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` binary(16) NULL DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` smallint(6) NULL DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  UNIQUE INDEX `trace_id_high`(`trace_id_high`, `trace_id`, `span_id`, `a_key`, `a_timestamp`) USING BTREE COMMENT 'Ignore insert on duplicate',
  INDEX `trace_id_high_2`(`trace_id_high`, `trace_id`, `span_id`) USING BTREE COMMENT 'for joining with zipkin_spans',
  INDEX `trace_id_high_3`(`trace_id_high`, `trace_id`) USING BTREE COMMENT 'for getTraces/ByIds',
  INDEX `endpoint_service_name`(`endpoint_service_name`) USING BTREE COMMENT 'for getTraces and getServiceNames',
  INDEX `a_type`(`a_type`) USING BTREE COMMENT 'for getTraces and autocomplete values',
  INDEX `a_key`(`a_key`) USING BTREE COMMENT 'for getTraces and autocomplete values',
  INDEX `trace_id`(`trace_id`, `span_id`, `a_key`) USING BTREE COMMENT 'for dependencies job'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compressed;

-- ----------------------------
-- Records of zipkin_annotations
-- ----------------------------

-- ----------------------------
-- Table structure for zipkin_dependencies
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_dependencies`;
CREATE TABLE `zipkin_dependencies`  (
  `day` date NOT NULL,
  `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `child` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `call_count` bigint(20) NULL DEFAULT NULL,
  `error_count` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`day`, `parent`, `child`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compressed;

-- ----------------------------
-- Records of zipkin_dependencies
-- ----------------------------

-- ----------------------------
-- Table structure for zipkin_spans
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_spans`;
CREATE TABLE `zipkin_spans`  (
  `trace_id_high` bigint(20) NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remote_service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `debug` bit(1) NULL DEFAULT NULL,
  `start_ts` bigint(20) NULL DEFAULT NULL COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` bigint(20) NULL DEFAULT NULL COMMENT 'Span.duration(): micros used for minDuration and maxDuration query',
  PRIMARY KEY (`trace_id_high`, `trace_id`, `id`) USING BTREE,
  INDEX `trace_id_high`(`trace_id_high`, `trace_id`) USING BTREE COMMENT 'for getTracesByIds',
  INDEX `name`(`name`) USING BTREE COMMENT 'for getTraces and getSpanNames',
  INDEX `remote_service_name`(`remote_service_name`) USING BTREE COMMENT 'for getTraces and getRemoteServiceNames',
  INDEX `start_ts`(`start_ts`) USING BTREE COMMENT 'for getTraces ordering and range'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compressed;

-- ----------------------------
-- Records of zipkin_spans
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
