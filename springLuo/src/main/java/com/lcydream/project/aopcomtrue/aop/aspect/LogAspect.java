package com.lcydream.project.aopcomtrue.aop.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * LogAspect
 *
 * @author Luo Chun Yun
 * @date 2018/7/6 21:46
 */
public class LogAspect {

	private final static Logger LOG = Logger.getLogger(LogAspect.class);

	public void before(JoinPoint joinpoint){
		LOG.info("调用方法之前执行"+joinpoint+":"+joinpoint.getTarget());
	}

	public void after(JoinPoint joinpoint){
		LOG.info("调用方法之后执行"+joinpoint+":"+joinpoint.getTarget());
	}

	public void afterReturn(Object object){
		LOG.info("获得返回值之后执行"+object+":");
	}

	public void afterThrow(JoinPoint joinpoint){
		LOG.info("抛出异常之后执行"+joinpoint+":"+joinpoint.getTarget());
	}

}
