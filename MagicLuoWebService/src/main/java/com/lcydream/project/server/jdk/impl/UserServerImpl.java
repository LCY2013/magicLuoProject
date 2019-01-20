package com.lcydream.project.server.jdk.impl;

import com.lcydream.project.server.jdk.service.UserServer;

import javax.jws.WebService;

/**
 * UserServerImpl
 *
 * @author Luo Chun Yun
 * @date 2018/8/25 10:15
 */
@WebService
public class UserServerImpl implements UserServer {

    private final String nickNameFinal = "magic";


    @Override
    public String checkNickName(String nickName) {
        if(nickNameFinal.equals(nickName)){
            return "true";
        }
        return "false";
    }

}
