package com.lcydream.project.provider;

import com.lcydream.project.framework.server.registry.RpcRegistry;

/**
 * ServerBootStrapMain
 * 提供者启动
 * @author Luo Chun Yun
 * @date 2018/11/11 15:28
 */
public class ServerBootStrapMain {

    public static void main(String[] args) {
        new RpcRegistry().start();
    }

}
