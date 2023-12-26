package com.xjs.cvs.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库表t_sys_user 映射为一个java实体类
 *
 *CREATE TABLE `t_sys_user`  (
 *   `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 *   `account` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
 *   `realName` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
 *   `password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
 *   `sex` int(0) NULL DEFAULT 1 COMMENT '性别（1:女、 2:男）',
 *   `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
 *   `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
 *   `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
 *   `roleId` int(0) NULL DEFAULT NULL COMMENT '用户角色（取自角色表-角色id）',
 *   `createUserId` bigint(0) NULL DEFAULT NULL COMMENT '创建者（userId）',
 *   `createDateTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
 *   `updateUserId` bigint(0) NULL DEFAULT NULL COMMENT '更新者（userId）',
 *   `updateDateTime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
 *   `idPicPath` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传个人证件照存储路径',
 *   `workPicPath` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传个人工作证照片存储路径',
 *   PRIMARY KEY (`id`) USING BTREE,
 *   INDEX `fk_role_id`(`roleId`) USING BTREE,
 *   CONSTRAINT `fk_role_id` FOREIGN KEY (`roleId`) REFERENCES `t_sys_role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
 *
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String account;
    private String realName;
    private String password;
    private Integer age;
    private Integer sex;
    private Date birthday;
    private String phone;
    private String address;
    private Integer roleId;
    private Integer createUserId;
    private Date createDateTime;
    private Integer updateUserId;
    private Date updateDateTime;
    private String idPicPath;
    private String workPicPath;

    // 关联角色表
    private Role role;



}
