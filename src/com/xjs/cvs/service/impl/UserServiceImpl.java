package com.xjs.cvs.service.impl;

import com.xjs.cvs.mapper.UserMapper;
import com.xjs.cvs.pojo.User;
import com.xjs.cvs.service.UserService;
import com.xjs.cvs.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    //编写持久层接口对象 -- 角色：属性
    private UserMapper userMapper;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User queryUser(String account) {
        User user = null;
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
        if (account!=null && !"".equals(account)){

            try {
                user = userMapper.findUserByAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return user;
    }

    @Override
    public int modifyUserPwd(Integer id, String password) {
        int n = 0;
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
        if (id!=null && password !=null){
            try {
                n =   userMapper.updateUserPwd(id,password);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            } finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return n;
    }

    @Override
    public User login(String account, String password) {
        User user = null;
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
        if (account!=null && !"".equals(account)){
            if (password!=null && !"".equals(password)){
                try {
                    user = userMapper.findUser(account,password);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    MybatisUtil.closeSqlSession();
                }

            }
        }
        return user;
    }

    @Override
    public List<User> queryAllUsers(String realName, Integer roleId) {
        List<User> userList = new ArrayList<>();
        userMapper = MybatisUtil.getSqlSession().getMapper(UserMapper.class);
        try {
            userList = userMapper.findAllUsers(realName,roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MybatisUtil.closeSqlSession();
        }
        return userList;
    }

    @Override
    public User queryUserByAccount(String account) {
        User user = null;
        if (account!=null && !"".equals(account)){
            userMapper = MybatisUtil.getSqlSession().getMapper(UserMapper.class);
            try {
                user =  userMapper.findUserByAccount(account);
                MybatisUtil.getSqlSession().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return user;
    }

    @Override
    public User queryUserById(Integer id) {
        User user = null;
        if (id!=null && id>0){
            userMapper = MybatisUtil.getSqlSession().getMapper(UserMapper.class);
            try {
                user = userMapper.findUserById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return user;
    }

    @Override
    public int addUser(User user) {
        int n =0;
        if (user!=null){
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            userMapper = sqlSession.getMapper(UserMapper.class);
            try {
                n =  userMapper.insertUser(user);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return n;
    }

    @Override
    public int removeUserById(Integer id) {
        int n =0;
        if (id!=null){
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            userMapper = sqlSession.getMapper(UserMapper.class);
            try {
                n =  userMapper.deleteUserById(id);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return n;
    }

    @Override
    public int modifyUser(User user) {
        int n =0;
        if (user!=null){
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            userMapper = sqlSession.getMapper(UserMapper.class);
            try {
                n =  userMapper.updateUser(user);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return n;
    }





}
