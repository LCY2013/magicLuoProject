package com.lcydream.project.framework.server.ioc.annotation;

import java.lang.annotation.*;

/**
 * CustomRequestMapping
 *
 * @author Luo Chun Yun
 * @date 2018/7/17 22:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CustomRequestMapping {
  String value() default "";
}
