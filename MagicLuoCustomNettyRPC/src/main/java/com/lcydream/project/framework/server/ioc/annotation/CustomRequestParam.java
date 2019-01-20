package com.lcydream.project.framework.server.ioc.annotation;

import java.lang.annotation.*;

/**
 * CustomRequestParam
 *
 * @author Luo Chun Yun
 * @date 2018/7/17 22:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface CustomRequestParam {
	String value() default "";
	boolean required() default true;
}
