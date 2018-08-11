package com.lcydream.project.executor;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.config.MagicConfiguration;
import com.lcydream.project.handler.StatementHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * CacheExecutor
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 20:44
 */
@Data
public class CacheExecutor implements Executor {

    private static final Logger logger = LoggerFactory.getLogger(CacheExecutor.class);

    private MagicConfiguration magicConfiguration;

    private SimpleExecutor delegate;
    /**
     * 缓存
     */
    private Map<String,Object> localCache = new HashMap();

    public CacheExecutor(MagicConfiguration magicConfiguration,SimpleExecutor delegate) {
        this.magicConfiguration = magicConfiguration;
        this.delegate = delegate;
    }

    public CacheExecutor(SimpleExecutor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T executor(String nameSpace, Object parameter) {
        //初始化StatementHandler --> ParameterHandler --> ResultSetHandler
        Object result;
        if(magicConfiguration.getMapperRegistory().get(nameSpace) != null) {
            result = localCache.get(magicConfiguration.getMapperRegistory().get(nameSpace).getSql());
            if (null != result) {
                logger.info("一级缓存命中:" + magicConfiguration.getMapperRegistory().get(nameSpace).getSql()
                        + " params:" + JSON.toJSONString(parameter));
                return (T) result;
            }
        }
        result =  (T) delegate.executor(nameSpace,parameter);
        if(magicConfiguration.getMapperRegistory().get(nameSpace).getSql().toUpperCase().startsWith(StatementHandler.SqlCommandType.INSERT.name())
            || magicConfiguration.getMapperRegistory().get(nameSpace).getSql().toUpperCase().startsWith(StatementHandler.SqlCommandType.UPDATE.name())
            || magicConfiguration.getMapperRegistory().get(nameSpace).getSql().toUpperCase().startsWith(StatementHandler.SqlCommandType.DELETE.name())) {
            localCache.clear();
        }else {
            localCache.put(magicConfiguration.getMapperRegistory().get(nameSpace).getSql(), result);
        }
        return (T)result;
    }
}
