package com.lcydream.project.framework.server.ioc.annotation;

import java.lang.annotation.*;

/**
 * CustomAwtowired
 *
 * @author Luo Chun Yun
 * @date 2018/7/17 22:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface CustomAwtowired {
	String value() default "";
}
