package com.jt.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@RestController
@ConfigurationProperties(prefix = "msg")
@Data		//get/set/toString
@Accessors(chain = true)	//开启链式加载结构
@NoArgsConstructor	//无参构造
@AllArgsConstructor	//全参构造
public class MsgController {
	/*@Value 表示从spring容器中动态获取数据.
	 * 通过spel表达式动态取值
	 * @Value("${msg.username}") 
	 * private String username;		//用户名
	 * @Value("${msg.age}") 
	 * private Integer age;			//用户名
	 */
	/** //方法二@ConfigurationProperties(prefix="msg") 配合lombok的@data注解
	 * 批量为属性赋值,必须配合set方法才能赋值
	 */
	private String  username;	//用户名
	private Integer age;

	@RequestMapping("/getMsg")
	public String getMsg() {
		//String str="springBoot测试方法";
		
		return "返回结果:"+username+"+"+age;
	}
}
