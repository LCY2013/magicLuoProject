package com.lcydream.project.mybatis.demo;

import com.lcydream.project.mybatis.beans.Posts;
import com.lcydream.project.mybatis.beans.Test;
import com.lcydream.project.mybatis.gp.config.mappers.TestMapper;
import com.lcydream.project.mybatis.mapper.PostsMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

public class MyBatisDemo {
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() throws FileNotFoundException {
        //配置文件
        InputStream configFile = new FileInputStream(
                "D:\\SoftWareTools\\idea201801\\ideaWork\\magicLuoProject\\mybatis-use\\src\\main\\java\\com\\lcydream\\project\\mybatis\\demo\\mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configFile);
        //加载配置文件得到SqlSessionFactory
        return sqlSessionFactory.openSession();
    }

    public static SqlSessionFactory getSqlSessionFactory() throws FileNotFoundException {
        InputStream configFile = new FileInputStream("D:\\SoftWareTools\\idea201801\\ideaWork\\magicLuoProject\\mybatis-use\\src\\main\\java\\com\\lcydream\\project\\mybatis\\demo\\mybatis-config.xml");
        if (null != sqlSessionFactory) {
            return sqlSessionFactory;
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configFile);
        return sqlSessionFactory;
    }

    public static Test get(SqlSession sqlSession, int id) throws SQLException {
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        long start = System.currentTimeMillis();
        Test test = testMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        test = testMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start) + "\n" + test);
//        test.setName(String.valueOf(new Random().nextInt(5)));
//        testMapper.updateByPrimaryKey(test);
        start = System.currentTimeMillis();
        test = testMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        return test;
    }

    public static Test getOne(SqlSession sqlSession, int id) throws SQLException {
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        long start = System.currentTimeMillis();
        Test test = testMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        return test;
    }

    public static Posts getPosts(SqlSession sqlSession, int id) {
        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        long start = System.currentTimeMillis();
        Posts posts = postsMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        posts = postsMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start) + "\n" + posts);
//        test.setName(String.valueOf(new Random().nextInt(5)));
//        testMapper.updateByPrimaryKey(test);
        start = System.currentTimeMillis();
        posts = postsMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        return posts;
    }


    public static int insert(SqlSession sqlSession, Test test) {
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        //return testMapper.insert(test);
        return 1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        SqlSession sqlSession = getSqlSession();
        try {
//            diffSession();
            System.out.println(getOne(sqlSession, 1));
//            System.out.println(getPosts(sqlSession, 1));
//            System.out.println(insert(sqlSession, new Test(null, 66, "test insert")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    public static void diffSession() throws Exception {
        SqlSession sqlSession1 = getSqlSessionFactory().openSession();
        SqlSession sqlSession2 = getSqlSessionFactory().openSession();
        SqlSession sqlSession3 = getSqlSessionFactory().openSession();
        getOne(sqlSession1, 1);
        sqlSession1.commit();
        sqlSession1.close();
        getOne(sqlSession2, 1);
        sqlSession2.commit();
        sqlSession2.close();
        getOne(sqlSession3, 1);
        sqlSession3.commit();
        sqlSession3.close();
    }
}
