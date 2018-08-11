package com.lcydream.project.mapper;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.config.MapperRegistory;
import com.lcydream.project.session.MagicSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * MapperProxy
 * 动态代理执行类
 * @author Luo Chun Yun
 * @date 2018/8/5 15:10
 */
public class MapperProxy<T> implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(MapperProxy.class);
    private final MagicSqlSession magicSqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(MagicSqlSession magicSqlSession, Class<T> mapperInterface) {
        this.magicSqlSession = magicSqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperRegistory.MapperData mapperData =
                magicSqlSession.getMagicConfiguration()
                        .getMapperRegistory()
                        .get(method.getDeclaringClass().getName() + "." + method.getName());
        if (null != mapperData) {
            if(args != null) {
                logger.info(String.format("SQL:  %s , parameter: %s ", mapperData.getSql(), JSON.toJSONString(args)));
            }else {
                logger.info(String.format("SQL:  %s , parameter: %s ", mapperData.getSql(), "null"));
            }
            if(args != null) {
                return magicSqlSession.handler(method.getDeclaringClass().getName() +
                        "." + method.getName(), args);
            }else {
                return magicSqlSession.handler(method.getDeclaringClass().getName() +
                        "." + method.getName(), null);
            }
        }
        return method.invoke(proxy, args);
    }

}
