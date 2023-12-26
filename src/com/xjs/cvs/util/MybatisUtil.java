package com.xjs.cvs.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 此类角色：工具类
 * 作用: 获取数据库连接，关闭数据库连接
 *
 * 属性文件
 *   jdbc.properties 连接数据库的参数
 *      数据库名称  密码 等等
 *   log4j.properties 记录打印SQL语句日志
 * Mybatis框架的配置文件
 *    mybatis_config.xml
 *    主要作用: 运用mybatis框架，减少向之前jdbc模式的代码
 *
 */
public class MybatisUtil {
    //属性作用: 针对于同一个操作，确保使用的数据库连接是同一个连接
    public static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();
    //确保使用工厂模式获取SqlSession对象
    public static SqlSessionFactory sqlSessionFactory = null;

    // 读取配置文件的内容   加载Mybatis框架
    static{
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession==null){
            sqlSession = sqlSessionFactory.openSession();
            threadLocal.set(sqlSession);
        }
        return sqlSession;
    }

    //关闭数据库连接
    public static void closeSqlSession(){
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession!=null){
            sqlSession.close();
        }
        threadLocal.set(null);
    }

    public static void main(String[] args) {
        System.out.println(getSqlSession());
    }


}
