package com.lcydream.project.executor;

import com.lcydream.project.config.MagicConfiguration;

/**
 * ExecutorFactory
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 20:37
 */
public class ExecutorFactory {

    private static final String SIMPLE = "SIMPLE";
    private static final String CACHING = "CACHING";

    public static Executor defaultExecutor(MagicConfiguration magicConfiguration){
        return get(ExecutorType.SIMPLE,magicConfiguration);
    }

    public static Executor get(ExecutorType executorType,MagicConfiguration magicConfiguration){
        if(SIMPLE.equals(executorType.name())){
            return new SimpleExecutor(magicConfiguration);
        }else if(CACHING.equals(executorType.name())){
            return new CacheExecutor(magicConfiguration,new SimpleExecutor(magicConfiguration));
        }
        return null;
    }

    public enum ExecutorType {
        /**
         * 简单执行器
         */
        SIMPLE,
        /**
         * 缓存执行器
         */
        CACHING
    }
}
