package com.lcydream.project.server.jdk.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * UserServer
 *  webServer 定义接口
 * @author Luo Chun Yun
 * @date 2018/8/25 10:13
 */
@WebService
public interface UserServer {

    @WebMethod
    String checkNickName(String nickName);
}
