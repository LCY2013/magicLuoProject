package com.lcydream.project.mybatis.gp.session;


import com.lcydream.project.mybatis.gp.config.GpConfiguration;
import com.lcydream.project.mybatis.gp.config.MapperRegistory;
import com.lcydream.project.mybatis.gp.executor.Executor;
import com.lcydream.project.mybatis.gp.mapper.MapperProxy;

import java.lang.reflect.Proxy;

public class GpSqlSession {
    private GpConfiguration configuration;
    private Executor executor;

    public GpConfiguration getConfiguration() {
        return configuration;
    }

    //关联起来
    public GpSqlSession(GpConfiguration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},new MapperProxy(this,clazz));
    }

    public <T> T selectOne(MapperRegistory.MapperData mapperData, Object parameter) throws Exception {
        return executor.query(mapperData, parameter);
    }
}
