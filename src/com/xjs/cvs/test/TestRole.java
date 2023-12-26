package com.xjs.cvs.test;

import com.xjs.cvs.mapper.RoleMapper;
import com.xjs.cvs.pojo.Role;
import com.xjs.cvs.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class TestRole {

    public SqlSession sqlSession;

    @Before
    public void before (){
        if (sqlSession == null)
        {
            sqlSession = MybatisUtil.getSqlSession();
        }

    }
    @Test
    public void testAdd(){
        RoleMapper mapper =sqlSession.getMapper(RoleMapper.class);
        Role role = new Role();
        role.setCode("SMBMS_EMPLOYEE3");
        role.setRoleName("销售员");
        role.setCreateUserId(1);
        role.setCreateDateTime(new Date());

        int i = mapper.addRole(role);
        sqlSession.commit();
        System.out.println(i>0?"添加成功!":"添加失败!");

    }
    @Test
    public void testFindAll(){
        RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
        List<Role> allRoles = mapper.findAllRoles();
        allRoles.forEach(System.out::println);




    }






}
