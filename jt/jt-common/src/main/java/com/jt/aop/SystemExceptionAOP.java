package com.jt.aop;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

//表示该类是全局异常处理机制类
@RestControllerAdvice  //advice-->通知    Rest-->返回的数据都是json串
@Slf4j		//添加日志
public class SystemExceptionAOP {
	/*
	 * 添加通用的异常返回的方法
	 * 底层原理:AOP的异常通知
	 */
	@ExceptionHandler(RuntimeException.class) //拦截运行时异常
	public Object systemResultException(Exception e) {
		
		e.printStackTrace();  		//如果有问题,则直接在控制台打印
		log.error("{~~~业务调用异常~~~}",e);
		return SysResult.fail();	//返回统一的失败数据
	}
}
