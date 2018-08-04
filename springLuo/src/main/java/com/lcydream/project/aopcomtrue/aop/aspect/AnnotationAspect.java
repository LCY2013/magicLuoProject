package com.lcydream.project.aopcomtrue.aop.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * LogAspect
 *
 * @author Luo Chun Yun
 * @date 2018/7/6 21:46
 */
//首先这个类申明是放入到spring容器中，如果不声明，就无法自动注入
@Component
//这个类被申明为一个需要被织入到切面中的类
@Aspect
public class AnnotationAspect {

	private final static Logger LOG = Logger.getLogger(AnnotationAspect.class);

	//声明点
	//因为需要利用反射机制去读取这个切面中的所有注解信息
	@Pointcut("execution(* com.lcydream.project.aopcomtrue.aop.service..*(..))")
	public void pointcutConfig(){

	}

	@Before("pointcutConfig()")
	public void before(JoinPoint joinpoint){
		LOG.info("调用方法之前执行"+joinpoint+":"+joinpoint.getTarget());
	}

	@After("pointcutConfig()")
	public void after(JoinPoint joinpoint){
		LOG.info("调用方法之后执行"+joinpoint+":"+joinpoint.getTarget());
	}

	@AfterReturning(value = "pointcutConfig()",returning = "object")
	public void afterReturn(JoinPoint joinpoint,Object object){
		LOG.info("获得返回值之后执行"+object+":");
	}

	@AfterThrowing("pointcutConfig()")
	public void afterThrow(JoinPoint joinpoint){
        System.out.println("切点的参数" + Arrays.toString(joinpoint.getArgs()));
        System.out.println("切点的方法" + joinpoint.getKind());
        System.out.println("getSignature方法,获取这个切点的信息" + joinpoint.getSignature());
		//生成以后的代理对象
        System.out.println("getTarget方法" + joinpoint.getTarget());
        //当前类本身（反射机制去调用）
        System.out.println("getThis方法" + joinpoint.getThis());
        System.out.println("getStaticPart方法" + joinpoint.getStaticPart());
		LOG.info("抛出异常之后执行"+joinpoint+":"+joinpoint.getTarget());
	}

}
