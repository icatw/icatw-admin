/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : vueadmin

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 09/05/2022 16:52:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int NOT NULL COMMENT '类型     0：目录   1：菜单   2：按钮',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `orderNum` int NULL DEFAULT NULL COMMENT '排序',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `statu` int NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', 'sys:manage', '', 0, 'el-icon-s-operation', 1, '2021-01-15 18:58:18', '2021-01-15 18:58:20', 1);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '/sys/users', 'sys:user:list', 'sys/User', 1, 'el-icon-s-custom', 1, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '/sys/roles', 'sys:role:list', 'sys/Role', 1, 'el-icon-rank', 2, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', '/sys/menus', 'sys:menu:list', 'sys/Menu', 1, 'el-icon-menu', 3, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (5, 0, '系统工具', '', 'sys:tools', NULL, 0, 'el-icon-s-tools', 2, '2021-01-15 19:06:11', NULL, 1);
INSERT INTO `sys_menu` VALUES (6, 5, '数字字典', '/sys/dicts', 'sys:dict:list', 'sys/Dict', 1, 'el-icon-s-order', 1, '2021-01-15 19:07:18', '2021-01-18 16:32:13', 1);
INSERT INTO `sys_menu` VALUES (7, 3, '添加角色', '', 'sys:role:save', '', 2, '', 1, '2021-01-15 23:02:25', '2021-01-17 21:53:14', 0);
INSERT INTO `sys_menu` VALUES (9, 2, '添加用户', NULL, 'sys:user:save', NULL, 2, NULL, 1, '2021-01-17 21:48:32', NULL, 1);
INSERT INTO `sys_menu` VALUES (10, 2, '修改用户', NULL, 'sys:user:update', NULL, 2, NULL, 2, '2021-01-17 21:49:03', '2021-01-17 21:53:04', 1);
INSERT INTO `sys_menu` VALUES (11, 2, '删除用户', NULL, 'sys:user:delete', NULL, 2, NULL, 3, '2021-01-17 21:49:21', NULL, 1);
INSERT INTO `sys_menu` VALUES (12, 2, '分配角色', NULL, 'sys:user:role', NULL, 2, NULL, 4, '2021-01-17 21:49:58', NULL, 1);
INSERT INTO `sys_menu` VALUES (13, 2, '重置密码', NULL, 'sys:user:repass', NULL, 2, NULL, 5, '2021-01-17 21:50:36', NULL, 1);
INSERT INTO `sys_menu` VALUES (14, 3, '修改角色', NULL, 'sys:role:update', NULL, 2, NULL, 2, '2021-01-17 21:51:14', NULL, 1);
INSERT INTO `sys_menu` VALUES (15, 3, '删除角色', NULL, 'sys:role:delete', NULL, 2, NULL, 3, '2021-01-17 21:51:39', NULL, 1);
INSERT INTO `sys_menu` VALUES (16, 3, '分配权限', NULL, 'sys:role:perm', NULL, 2, NULL, 5, '2021-01-17 21:52:02', NULL, 1);
INSERT INTO `sys_menu` VALUES (17, 4, '添加菜单', NULL, 'sys:menu:save', NULL, 2, NULL, 1, '2021-01-17 21:53:53', '2021-01-17 21:55:28', 1);
INSERT INTO `sys_menu` VALUES (18, 4, '修改菜单', NULL, 'sys:menu:update', NULL, 2, NULL, 2, '2021-01-17 21:56:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (19, 4, '删除菜单', NULL, 'sys:menu:delete', NULL, 2, NULL, 3, '2021-01-17 21:56:36', NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL,
  `statu` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE,
  UNIQUE INDEX `code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (3, '普通用户', 'normal', '只有基本查看功能', '2021-01-04 10:09:14', '2021-01-30 08:19:52', 1);
INSERT INTO `sys_role` VALUES (6, '超级管理员', 'admin', '系统默认最高权限，不可以编辑和任意修改', '2021-01-16 13:29:03', '2021-01-17 15:50:45', 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (106, 3, 5);
INSERT INTO `sys_role_menu` VALUES (107, 3, 6);
INSERT INTO `sys_role_menu` VALUES (108, 3, 1);
INSERT INTO `sys_role_menu` VALUES (109, 3, 4);
INSERT INTO `sys_role_menu` VALUES (110, 3, 3);
INSERT INTO `sys_role_menu` VALUES (111, 3, 2);
INSERT INTO `sys_role_menu` VALUES (129, 6, 5);
INSERT INTO `sys_role_menu` VALUES (130, 6, 6);
INSERT INTO `sys_role_menu` VALUES (131, 6, 1);
INSERT INTO `sys_role_menu` VALUES (132, 6, 4);
INSERT INTO `sys_role_menu` VALUES (133, 6, 19);
INSERT INTO `sys_role_menu` VALUES (134, 6, 18);
INSERT INTO `sys_role_menu` VALUES (135, 6, 17);
INSERT INTO `sys_role_menu` VALUES (136, 6, 3);
INSERT INTO `sys_role_menu` VALUES (137, 6, 16);
INSERT INTO `sys_role_menu` VALUES (138, 6, 15);
INSERT INTO `sys_role_menu` VALUES (139, 6, 14);
INSERT INTO `sys_role_menu` VALUES (140, 6, 7);
INSERT INTO `sys_role_menu` VALUES (141, 6, 2);
INSERT INTO `sys_role_menu` VALUES (142, 6, 13);
INSERT INTO `sys_role_menu` VALUES (143, 6, 12);
INSERT INTO `sys_role_menu` VALUES (144, 6, 11);
INSERT INTO `sys_role_menu` VALUES (145, 6, 10);
INSERT INTO `sys_role_menu` VALUES (146, 6, 9);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime NULL DEFAULT NULL,
  `updated` datetime NULL DEFAULT NULL,
  `last_login` datetime NULL DEFAULT NULL,
  `statu` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_USERNAME`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$wvkI1vMfdLFtD8ZFImD1F.VbxLHFotn.JynivWmgfcrcwkAIGAuZ2', 'https://blog-icatwms.oss-cn-beijing.aliyuncs.com/2022/05-07/28ec424aa0984540b756426f2221e5d738164d1367b440d58b7ed77c702f01db219004018cc2414e83dd5aafd6c163d9.jpg', '17374630398', '762188827@qq.com', '长沙', '2021-01-12 22:13:53', '2022-05-07 20:14:39', '2022-05-09 16:40:29', 1);
INSERT INTO `sys_user` VALUES (2, 'test', '$2a$10$1EIZd6VQUJ91IAT0/fCs7.mHIFei913Rs2JVGzeXJI.jAohBgFTaO', 'https://blog-icatwms.oss-cn-beijing.aliyuncs.com/2022/05-07/28ec424aa0984540b756426f2221e5d738164d1367b440d58b7ed77c702f01db219004018cc2414e83dd5aafd6c163d9.jpg', NULL, 'test@qq.com', NULL, '2021-01-30 08:20:00', '2022-05-07 20:22:02', NULL, 1);
INSERT INTO `sys_user` VALUES (3, 'icatw', '$2a$10$c2lXFYRIOMmp0XsKdzlcuOjUwLL2u/uoMX.rhRagZWgnUpxxFn/dm', 'https://blog-icatwms.oss-cn-beijing.aliyuncs.com/2022/05-07/28ec424aa0984540b756426f2221e5d738164d1367b440d58b7ed77c702f01db219004018cc2414e83dd5aafd6c163d9.jpg', NULL, '762188827@qq.com', '湖南', NULL, '2022-05-07 20:13:28', NULL, 1);
INSERT INTO `sys_user` VALUES (8, 'test2', '$2a$10$jpsfRH.UmEY.suOzV3XK3efSMS9JaosLIA/ctZjrtnZ8JKaKfzk7S', 'https://blog-icatwms.oss-cn-beijing.aliyuncs.com/2022/05-07/28ec424aa0984540b756426f2221e5d738164d1367b440d58b7ed77c702f01db219004018cc2414e83dd5aafd6c163d9.jpg', '11', '11@qq.com', NULL, '2022-05-07 20:47:19', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (4, 1, 6);
INSERT INTO `sys_user_role` VALUES (7, 1, 3);
INSERT INTO `sys_user_role` VALUES (13, 2, 3);
INSERT INTO `sys_user_role` VALUES (14, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;