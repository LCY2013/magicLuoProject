package com.lcydream.project.provider.service;

import com.lcydream.project.framework.server.ioc.annotation.CustomService;
import com.lcydream.project.provider.api.IUserService;

/**
 * UserServiceImpl
 * 用户服务
 * @author Luo Chun Yun
 * @date 2018/11/11 15:45
 */
@CustomService("userService")
public class UserServiceImpl implements IUserService {

    @Override
    public String login(String name, String passWord) {
        if(name.equalsIgnoreCase(passWord)) {
            return "欢迎 " + name;
        }else {
            return "登录失败";
        }
    }

}
