package com.legou.comment.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SJXY
 */
@Target({ElementType.PARAMETER}) //参数级别
@Retention(RetentionPolicy.RUNTIME)  //注解保留到运行阶段
public @interface ParamNOTNUll {
}
