package com.lcydream.project.server.jdk;


import com.lcydream.project.server.jdk.impl.UserServerImpl;

import javax.xml.ws.Endpoint;

/**
 * BootStrap
 * 启动类
 * @author Luo Chun Yun
 * @date 2018/8/25 10:09
 */
public class BootStrap {

    public static void main(String[] args) {
        Endpoint.publish("http://127.0.0.1:8888/ws/user",new UserServerImpl());
        System.out.println("webService starting");
    }

}
