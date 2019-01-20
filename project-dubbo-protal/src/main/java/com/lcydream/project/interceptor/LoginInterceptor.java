package com.lcydream.project.interceptor;

import com.alibaba.dubbo.common.json.JSON;
import com.lcydream.project.controller.BaseController;
import com.lcydream.project.controller.support.Anonymous;
import com.lcydream.project.entity.Request;
import com.lcydream.project.entity.Response;
import com.lcydream.project.enums.ResponseEnum;
import com.lcydream.project.login.IUserService;
import com.lcydream.project.request.UserLoginRequest;
import com.lcydream.project.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * LoginInterceptor
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/17 17:45
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 获取头文件的token信息
	 */
	private final static String ACCESS_TOKEN="access_token";

	@Autowired
	IUserService userService;

	/**
	 * 日志组件
	 */
	private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


	@Override
	public boolean preHandle(HttpServletRequest request,
	                         HttpServletResponse response,
	                         Object handler) throws Exception {
		//获取处理handler的对象，转换成HandlerMethod对象
		HandlerMethod handlerMethod = (HandlerMethod)handler;

		//获取处理HandlerMapping
		Object action = handlerMethod.getBean();
		//如果获取的Controller没有继承baseController，则视为不合法的访问
		if(!(action instanceof BaseController)){
			throw new RuntimeException("不合法的请求类:"+action.getClass()
					+",没有继承"+BaseController.class);
		}

		//判断是否在这个方法或者类上面加了自定义放行注解，如果加了就直接开放拦截器
		if(isAnonymous(handlerMethod)){
			return true;
		}
		//获取Cookie中的JWT Token
		String accessToken = CookieUtil.getValue(request, ACCESS_TOKEN);
		//判断token是否为空，为空则是没有登录
		if(StringUtils.isBlank(accessToken)) {
			//判断此次请求是否是ajax请求
			if (CookieUtil.isAjax(request)) {
				Response data=new Response();
				data.setMessage(ResponseEnum.FAILED.getMessage());
				data.setCode(ResponseEnum.FAILED.getCode());
				response.getWriter().write(JSON.json(data));
				return false;
			}
			//跳转到登录页面
			response.sendRedirect("/login.shtml");
			return false;
		}
		//设置checkAuth对象，检查对象是否合法
		Request requestVal = new Request();
		requestVal.setToken(accessToken);
		//获取鉴权结果
		Response responseRet = userService.checkAuth(requestVal);
		//判断响应是否成功
		if(ResponseEnum.SUCCESS.getCode().intValue()==
				responseRet.getCode().intValue()){
			((BaseController)action)
					.setUserId(
							String.valueOf(((Map)responseRet.getData()).get("id")));
			//判断是否需要刷新
			if(String.valueOf(((Map)responseRet.getData()).get("flush"))!=null &&
					"yes".equals(String.valueOf(((Map)responseRet.getData()).get("flush")))){
				//当需要刷新的时候，新建一个用户登录请求参数
				UserLoginRequest userLoginRequest =
						new UserLoginRequest();
				userLoginRequest.setName(
						String.valueOf(((Map)responseRet.getData()).get("username")));
				userLoginRequest.setPassword(
						String.valueOf(((Map)responseRet.getData()).get("password")));
				//重新登录，校验并且获取用户token信息
				Response login = userService.login(userLoginRequest);
				//校验成功后重新覆盖cookie中的token值
				if(ResponseEnum.SUCCESS.getCode().intValue()
						== login.getCode().intValue()) {
					Map<String,Object> responseMap = (Map)login.getData();
					response.addHeader("Set-cookie", "access_token="
							+responseMap.get("token")+";path=/;HttpOnly");
					logger.info(JSON.json(responseRet)+"刷新Token:"+JSON.json(responseMap));
				}else {
					response.sendRedirect("/login.shtml");
					return false;
				}
			}
			return true;
		}
		response.sendRedirect("/login.shtml");
		return false;
	}

	private boolean isAnonymous(HandlerMethod handlerMethod){
		Object action = handlerMethod.getBean();
		Class<?> clazz = action.getClass();
		if(clazz.getAnnotation(Anonymous.class)!=null){
			return true;
		}
		Method method = handlerMethod.getMethod();
		return method.getAnnotation(Anonymous.class)!=null;
	}
}
