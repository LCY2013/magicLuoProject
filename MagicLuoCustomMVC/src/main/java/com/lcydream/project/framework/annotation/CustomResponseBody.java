package com.lcydream.project.framework.annotation;

import java.lang.annotation.*;

/**
 * CustomResponseBody
 *
 * @author Luo Chun Yun
 * @date 2018/7/17 22:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CustomResponseBody {
	String value() default "";
}
