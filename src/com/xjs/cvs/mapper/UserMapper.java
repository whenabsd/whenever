package com.xjs.cvs.mapper;




import com.xjs.cvs.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 针对于用户操作的持久层接口
 *
 *  查询全部  登录   模糊查询  增加  删除  修改等操作
 *
 */
public interface UserMapper {

        //根据账号密码进行查询/登录
        public User  findUser(@Param("account") String account,
                              @Param("password") String password);

        //根据指定账户，查询是否存在于用户表中
        public User findUserByAccount(@Param("account")String account);

        //修改指定账户的密码
        public int updateUserPwd(@Param("id")Integer id,@Param("password") String password);

        /**
         * 该方法查询数据库表t_sys_user  指定的数据
         * 情况1： 如果没有输入任何参数，默认查询表中所有的数据
         * 情况2:  如果只输入一个查询的条件，则按照指定的条件显示数据；
         * 情况3： 如果两个参数都输入，那么按照指定两个条件进行查询，并显示到页面；
         * @param realName
         * @param roleId
         * @return
         * @throws Exception
         */
        public List<User> findAllUsers(@Param("realName") String realName,
                                       @Param("roleId") Integer roleId) ;


        /**
         * 根据用户的id编号进行查询指定用户
         * @param id
         * @return
         * @throws Exception
         */
        public User findUserById(@Param("id") Integer id) throws Exception;

        /**
         * 向数据表添加指定用户信息
         * @param user  指定用户对象信息
         * @return  如果添加成功，则返回添加用户的记录数，反之，返回0
         * @throws Exception
         */
        public int insertUser(User user) ;

        //删除用户
        int deleteUserById(@Param("id") Integer id);

        //修改指定用户的信息
        int  updateUser(User user);

    }

