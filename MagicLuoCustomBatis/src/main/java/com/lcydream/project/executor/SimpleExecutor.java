package com.lcydream.project.executor;

import com.lcydream.project.config.MagicConfiguration;
import com.lcydream.project.handler.StatementHandler;
import lombok.Data;

/**
 * SimpleExecutor
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 20:44
 */
@Data
public class SimpleExecutor implements Executor {

    private MagicConfiguration magicConfiguration;

    public SimpleExecutor(MagicConfiguration magicConfiguration) {
        this.magicConfiguration = magicConfiguration;
    }

    @Override
    public <T> T executor(String statement, Object parameter) {
        StatementHandler handler = new StatementHandler(magicConfiguration);
        return (T)handler.handler(statement,parameter);
    }
}
