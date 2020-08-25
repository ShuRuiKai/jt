package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)				//对谁有效
@Retention(RetentionPolicy.RUNTIME)		//有效期
public @interface CacheFind {
	public String key();	//标识存入redis的key的前缀
	public int secondes() default 0;	//标识保存时间  单位秒
}

