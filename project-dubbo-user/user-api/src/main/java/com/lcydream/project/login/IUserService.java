package com.lcydream.project.login;

import com.lcydream.project.entity.Request;
import com.lcydream.project.entity.Response;
import com.lcydream.project.request.UserLoginRequest;
import com.lcydream.project.request.UserRegisterRequest;

/**
 * IUserService
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 10:25
 */
public interface IUserService {

    /**
     * 登录
     * @param request 登录参数
     * @return 响应参数
     */
    Response login(UserLoginRequest request);

    /**
     * 修改姓名
     * @param request 请求参数
     * @return Response
     */
    Response debit(Request request);

    /**
     * 注册新用户
     * @param userInfo 用户信息
     * @return 返回响应码
     */
    Response register(UserRegisterRequest userInfo);

    /**
     * 检查权限是否存在
     * @param request 请求实体
     * @return 返回响应信息
     */
    Response checkAuth(Request request);
}
