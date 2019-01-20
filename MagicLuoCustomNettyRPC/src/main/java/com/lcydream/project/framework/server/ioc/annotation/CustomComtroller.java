package com.lcydream.project.framework.server.ioc.annotation;

import java.lang.annotation.*;

/**
 * CustomComtroller
 *
 * @author Luo Chun Yun
 * @date 2018/7/17 22:02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CustomComtroller {
	String value() default "";
}
