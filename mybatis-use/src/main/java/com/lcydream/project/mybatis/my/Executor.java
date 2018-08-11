package com.lcydream.project.mybatis.my;

public interface Executor {
    <E> E query(String statement, Object parameter);
}