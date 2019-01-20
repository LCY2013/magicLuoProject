package com.lcydream.project.client.jdk;


/**
 * BootSrapClient
 *
 * @author Luo Chun Yun
 * @date 2018/8/25 10:54
 */
public class BootSrapClient {

    public static void main(String[] args) {
        UserServerImplService userServerImplService =
                new UserServerImplService();
        UserServerImpl userServerImpl =
                userServerImplService.getUserServerImplPort();
        System.out.println(userServerImpl.checkNickName("magic"));
    }
}
