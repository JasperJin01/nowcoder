package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
}

// NOTE LoginRequired 是自定义的注解
//  ElementType.METHOD 表示这个注解只能标注在方法上，不能用在类、字段等其他地方
//  Retention(RetentionPolicy.RUNTIME) 表示 运行时可以通过反射机制获取这个注解的信息、
//  实现登录时检查的思路：利用 AOP，在需要检查的函数前加入注解，同时用拦截器拦截这些注解，在拦截器中判断是否登录