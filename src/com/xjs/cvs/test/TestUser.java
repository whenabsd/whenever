package com.xjs.cvs.test;

import com.xjs.cvs.mapper.UserMapper;
import com.xjs.cvs.pojo.User;
import com.xjs.cvs.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class TestUser {

    public SqlSession sqlSession;

    @Before
    public  void  before(){
        if (sqlSession == null){
            sqlSession = MybatisUtil.getSqlSession();
        }
    }

    @Test
    public void testFindUser(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(null);
        user.setAccount("admin");
        user.setRealName("admin");
        user.setPassword("111111");
        user.setAge(18);
        user.setSex(1);
        user.setBirthday(new Date());
        user.setRoleId(3);
        int i = mapper.insertUser(user);
        System.out.println(i>0?"添加成功!":"添加失败!");
    }


}
