package com.lcydream.project.executor;

/**
 * Executor
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 14:57
 */
public interface Executor {
    <T> T executor(String nameSpace, Object parameter);
}
