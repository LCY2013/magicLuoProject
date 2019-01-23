package com.lcydream.project.nginx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IndexController
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/20 17:05
 */
@RestController
public class IndexController {

	/**
	 * 日志组件
	 */
	Logger logger = LoggerFactory.getLogger(IndexController.class);

	@GetMapping("/getIp")
	public String getIp(HttpServletRequest request) throws UnknownHostException {
		String remoteAddr = request.getRemoteAddr();
		String realIp = request.getHeader("X-Real_IP");
		String interface_version = request.getHeader("interface_version");
		//获取本机ip
		InetAddress addr = InetAddress.getLocalHost();
		//获取本机计算机名称
		String localIp=addr.getHostAddress();
		String localHostName=addr.getHostName();
		logger.info("remote["+remoteAddr+"->"+realIp+"->"+interface_version+"]"+
				"local["+localIp+"->"+localHostName+"]");
		return "remote["+remoteAddr+"->"+realIp+"->"+interface_version+"]\n"+
				"local["+localIp+"->"+localHostName+"]";
	}
}
