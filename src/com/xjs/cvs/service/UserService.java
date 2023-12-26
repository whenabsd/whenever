package com.xjs.cvs.service;

import com.xjs.cvs.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 此接口 -- 处于业务逻辑层
 *
 * 向上，可以传递数据给Controller层
 * 向下，可以调用mapper持久层
 *
 */
public interface UserService {

    //登录的方法
    public User login(String account,String password);

    //根据账户查询数据库表user是否存在
    public User queryUser(String account);

    //修改指定账户的密码
    public int modifyUserPwd(@Param("id")Integer id, @Param("password") String password);

    //根据条件查询用户表所有符合条件的数据
    public List<User> queryAllUsers(@Param("realName") String realName,
                                    @Param("roleId") Integer roleId);
    //添加用户操作时，验证用户输入的账户不能和数据库表已存在的账户冲突
    public User queryUserByAccount(@Param("account") String account);

    //根据id编号查询指定用户信息
    public User queryUserById(@Param("id") Integer id) ;

    //添加用户的方法
    public int addUser(User user) ;

    //删除指定编号的用户信息
    int removeUserById(Integer id);

    //修改指定用户信息
    int modifyUser(User user);
}