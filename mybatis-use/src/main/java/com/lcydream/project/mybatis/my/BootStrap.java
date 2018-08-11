package com.lcydream.project.mybatis.my;


import com.lcydream.project.mybatis.beans.Test;

public class BootStrap {
    public static void start(){
        MySqlSession sqlSession = new MySqlSession();
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        Test test = testMapper.selectByPrimaryKey(1);
        System.out.println(test);
    }

    public static void main(String[] args) {
        start();
    }
}