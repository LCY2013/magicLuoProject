package com.lcydream.project.session;

import com.lcydream.project.config.MagicConfiguration;
import com.lcydream.project.executor.Executor;
import com.lcydream.project.mapper.MapperProxy;

import java.lang.reflect.Proxy;

/**
 * MagicSqlSession
 * 管理数据库层的会话
 * @author Luo Chun Yun
 * @date 2018/8/5 14:57
 */
public class MagicSqlSession {

    private MagicConfiguration magicConfiguration;

    private Executor executor;

    public MagicConfiguration getMagicConfiguration() {
        return magicConfiguration;
    }

    /**
     * 管理会话内容需要的参数
     * @param magicConfiguration 配置信息
     * @param executor 执行器
     */
    public MagicSqlSession(MagicConfiguration magicConfiguration, Executor executor) {
        this.magicConfiguration = magicConfiguration;
        this.executor = executor;
    }

    /**
     * 获取映射内容
     * @param type mapper类类型
     * @param <T> 泛型类型
     * @return 泛型
     */
    @SuppressWarnings("all")
    public <T> T getMapper(Class<T> type) {
        return (T)Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                new <T>MapperProxy(this,type));
    }

    /**
     * 查询表的某一行数据
     * @param nameSpace 执行命名空间
     * @param parameter 参数名称
     * @param <T> 泛型
     * @return 返回查询对象
     */
    public <T> T handler(String nameSpace, Object parameter) {
        return executor.executor(nameSpace,parameter);
    }

}
