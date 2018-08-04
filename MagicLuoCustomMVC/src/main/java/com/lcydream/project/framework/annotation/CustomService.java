package com.lcydream.project.framework.annotation;

import java.lang.annotation.*;

/**
 * CustomService
 *
 * @author Luo Chun Yun
 * @date 2018/7/17 22:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CustomService {
	String value() default "";
}
