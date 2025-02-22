package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 该注解可以应用于方法
@Retention(RetentionPolicy.RUNTIME) // 该注解在运行时保留
public @interface ClearCache {
    String categoryId() default ""; // 默认值为空，表示清理所有缓存
}
