package com.xyh.authorityManagement.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author xyh
 * @date 2021/11/14 9:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnno {
    /**
    * @description: 记录日志的操作类型
    * @return: java.lang.String
    * @author xyh
    * @date: 2021/11/14 9:39
    */
    String operateType();
}
