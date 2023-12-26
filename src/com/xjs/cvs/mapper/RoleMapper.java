package com.xjs.cvs.mapper;

import com.xjs.cvs.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 * 主要针对角色表的数据进行CRUD操作
 *
 */

public interface RoleMapper {

    //查询全部的角色信息
    @Select(value = "select * from t_sys_role")
    public List<Role> findAllRoles();

    //根据角色编码查询数据库是否存在指定的角色编码
    @Select("select * from t_sys_role where code = #{code}")
    public Role findRoleByCode(@Param("code") String code);

    //根据角色编号查询指定的角色记录
    @Select("select * from t_sys_role where id = #{id}")
    public Role findRoleById(@Param("id") Integer id);

    //添加角色信息
    @Insert("insert into t_sys_role values(null,#{code},#{roleName},#{createUserId},#{createDateTime},#{updateUserId},#{updateDateTime})")
    public int addRole(Role role);
    //修改角色信息
    @Update("update t_sys_role set roleName = #{roleName},updateUserId = #{updateUserId},updateDateTime = #{updateDateTime} where id = #{id} ;")
    public int updateRole(Role role);
    //删除角色信息
    @Delete("delete from t_sys_role where id = #{id}")
    public int deleteById(@Param("id") Integer id);


}
