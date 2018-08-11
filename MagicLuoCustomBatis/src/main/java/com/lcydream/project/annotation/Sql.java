package com.lcydream.project.annotation;

import java.lang.annotation.*;

/**
 * Sql
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 21:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Sql {

    String sql() default "";


}
