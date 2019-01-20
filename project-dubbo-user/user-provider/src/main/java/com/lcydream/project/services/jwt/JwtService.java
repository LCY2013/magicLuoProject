package com.lcydream.project.services.jwt;

import com.lcydream.project.dal.entity.User;
import com.lcydream.project.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JwtService
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/17 15:43
 */
@Component
public class JwtService {

	@Value("${expire.time}")
	private long expireTime;

	public String generatorJWT(User user){
		return JwtTokenUtils.generatorJWT(expireTime,user);
	}

	public Claims parseJWT(String token) {
		return JwtTokenUtils.parseJWT(token);
	}

}
