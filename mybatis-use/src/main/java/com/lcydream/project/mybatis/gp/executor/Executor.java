package com.lcydream.project.mybatis.gp.executor;


import com.lcydream.project.mybatis.gp.config.MapperRegistory;

public interface Executor {

    <T> T query(MapperRegistory.MapperData mapperData, Object parameter) throws Exception;
}
