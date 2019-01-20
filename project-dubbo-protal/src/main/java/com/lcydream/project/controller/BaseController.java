package com.lcydream.project.controller;

import org.springframework.stereotype.Controller;

/**
 * BaseController
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/18 11:02
 */
@Controller
public class BaseController {

	ThreadLocal<String> userIdLocal = new ThreadLocal<>();

	/**
	 * 设置userId
	 * @param userId 用户ID
	 */
	public void setUserId(String userId){
		userIdLocal.set(userId);
	}

	/**
	 * 获取用户ID
	 * @return 返回用户ID
	 */
	public String getUserId(){
		return userIdLocal.get();
	}
}
