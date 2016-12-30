package com.chendurex.mybatis.anaotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chen
 * @description
 * @pachage com.chendurex.mybatis.anaotation
 * @date 2016/12/30 20:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Condition {
    boolean check() default true;
    boolean where() default true;
}
