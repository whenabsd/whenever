package com.xjs.cvs.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create table t_supplier(
 *    `id` bigint(20) primary key AUTO_INCREMENT COMMENT '主键ID',
 *   `supCode` varchar(20) COMMENT '供应商编码',
 *   `supName` varchar(20) COMMENT '供应商名称',
 *   `supDesc` varchar(50) COMMENT '供应商详细描述',
 *   `supContact` varchar(20)  COMMENT '供应商联系人',
 *   `supPhone` varchar(20) COMMENT '联系电话',
 *   `supAddress` varchar(50)  COMMENT '地址',
 *   `supFax` varchar(20) COMMENT '传真',
 *   `createdUserId` bigint(20) COMMENT '创建者（userId）',
 *   `createdTime` datetime  COMMENT '创建时间',
 *   `updatedUserId` bigint(20) COMMENT '更新时间',
 *   `updatedTime` datetime COMMENT '更新者（userId）',
 *   `companyLicPicPath` varchar(255) COMMENT '企业营业执照图片保存路径',
 *   `orgCodePicPath` varchar(255) COMMENT '组织机构代码证图片保存路径'
 * );
 */
@Data
public class Supplier implements Serializable {
    private Integer id;
    private String supCode;
    private String supName;
    private String supDesc;
    private String supContact;
    private String supPhone;
    private String supAddress;
    private String supFax;
    private Integer createdUserId;
    private Date createdTime;
    private Integer updatedUserId;
    private Date updatedTime;
    private String companyLicPicPath;
    private String orgCodePicPath;
    //关联对象
    private User user;


}
