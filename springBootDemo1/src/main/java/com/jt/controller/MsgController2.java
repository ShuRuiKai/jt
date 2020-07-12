package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//默认条件下 使用ISO-8859-1的编码格式,所以中文乱码
@PropertySource(value="classpath:/properties/msg.propertiies",encoding="UTF-8")
public class MsgController2 {
	@Value("${msg.username2}") 
	private String  username;	//用户名
	@Value("${msg.age2}") 
	private Integer age;


	@RequestMapping("/getMsg2")
	public String getMsg() {
		//String str="springBoot测试方法";
		return "返回结果:"+username+"+"+age;
	}
}
