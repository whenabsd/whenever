package com.xjs.cvs.service;

import com.xjs.cvs.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 当前接口为角色的业务层接口
 */
public interface RoleService {
    //查询全部角色
    public List<Role> queryAllRoles();
    //根据角色编码查询数据库是否存在指定的角色编码
    public Role queryRoleByCode(@Param("code") String code);

    //添加指定的Role对象到数据库中锋的
    int insertRole(Role role);

    //根据角色id查询角色记录
    Role queryRoleById(Integer id);

    //山粗指定编号的role对象/记录
    int removeRoleById(Integer id);

    //修改指定编号的角色信息
    int modifyRole(Role role);

}
