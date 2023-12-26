package com.xjs.cvs.service.impl;

import com.xjs.cvs.mapper.RoleMapper;
import com.xjs.cvs.pojo.Role;
import com.xjs.cvs.service.RoleService;
import com.xjs.cvs.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService{
    private RoleMapper roleMapper;


    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public int removeRoleById(Integer id) {
        int n =0;
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        roleMapper = sqlSession.getMapper(RoleMapper.class);

        if (id!=null){
            try {
                n =  roleMapper.deleteById(id);
                //注意事项  此处必须进行提交一次 否则数据库表中无法正确添加
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                //注意事项  如果try代码块中出现异常，此处应该进行事务回滚
                sqlSession.rollback();
            } finally {
                //关闭数据库连接
                MybatisUtil.closeSqlSession();
            }
        }

        return n;
    }

    @Override
    public int modifyRole(Role role) {
        int n =0;
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        roleMapper = sqlSession.getMapper(RoleMapper.class);

        if (role!=null){
            try {
                n =  roleMapper.updateRole(role);
                //注意事项  此处必须进行提交一次 否则数据库表中无法正确添加
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                //注意事项  如果try代码块中出现异常，此处应该进行事务回滚
                sqlSession.rollback();
            } finally {
                //关闭数据库连接
                MybatisUtil.closeSqlSession();
            }
        }

        return n;
    }

    @Override
    public int insertRole(Role role) {
        int n =0;
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        roleMapper = sqlSession.getMapper(RoleMapper.class);

        if (role!=null){
            try {
                n =  roleMapper.addRole(role);
                //注意事项  此处必须进行提交一次 否则数据库表中无法正确添加
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                //注意事项  如果try代码块中出现异常，此处应该进行事务回滚
                sqlSession.rollback();
            } finally {
                //关闭数据库连接
                MybatisUtil.closeSqlSession();
            }
        }

        return n;
    }

    /**
     * 根据指定的id查询role表中指定的记录并返回一个role对象
     * @param id
     * @return
     */
    @Override
    public Role queryRoleById(Integer id) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        roleMapper = sqlSession.getMapper(RoleMapper.class);
        Role role = null;
        if (id!=null){
            try {
                role =  roleMapper.findRoleById(id);
                //注意事项  此处必须进行提交一次 否则数据库表中无法正确添加
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                //关闭数据库连接
                MybatisUtil.closeSqlSession();
            }
        }

        return role;
    }

    @Override
   public Role queryRoleByCode(String code){

        SqlSession sqlSession = MybatisUtil.getSqlSession();
        roleMapper = sqlSession.getMapper(RoleMapper.class);
        Role role = null;

            try {
                 role =  roleMapper.findRoleByCode(code);
                //注意事项  此处必须进行提交一次 否则数据库表中无法正确添加
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                //关闭数据库连接
                MybatisUtil.closeSqlSession();
            }
        return role;
    }

    /**
     * 查询全部的角色信息
     * @return
     */
    @Override
    public List<Role> queryAllRoles() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        roleMapper = sqlSession.getMapper(RoleMapper.class);
        List<Role> roleList = new ArrayList<>();
        try {
            roleList = roleMapper.findAllRoles();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MybatisUtil.closeSqlSession();
        }


        return roleList;
    }


}
