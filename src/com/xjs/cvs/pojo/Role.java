package com.xjs.cvs.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *依据数据库表t_sys_role  映射一个java类
 *
 *
 * @Data 注释的作用：为当前实体类的属性赋值get set方法，以及构造器(只能是空参构造器)
 *        toString()
 *
 *
 *
 */
@Data
public class Role implements Serializable {

    private Integer id;
    private String code;
    private String roleName;
    private Integer createUserId;
    private Date createDateTime;
    private Integer updateUserId;
    private Date updateDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", roleName='" + roleName + '\'' +
                ", createUserId=" + createUserId +
                ", createDateTime=" + createDateTime +
                ", updateUserId=" + updateUserId +
                ", updateDateTime=" + updateDateTime +
                '}';
    }
}
