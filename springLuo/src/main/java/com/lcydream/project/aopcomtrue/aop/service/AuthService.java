package com.lcydream.project.aopcomtrue.aop.service;

import com.lcydream.project.aopcomtrue.model.Member;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthService
 *
 * @author Luo Chun Yun
 * @date 2018/7/6 21:34
 */
@Service
public class AuthService {

	private final static Logger LOG = Logger.getLogger(AuthService.class);

	@Transactional
	public Member login(String loginName,String loginPass){
		LOG.info("用户登录");
		return null;
	}

	public boolean logout(String loginName){
		LOG.info("用户注销");
		return true;
	}
}
