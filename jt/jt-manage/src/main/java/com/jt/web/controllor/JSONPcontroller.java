package com.jt.web.controllor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;

@RestController
public class JSONPcontroller {
	
	/**
	 * http://manage.jt.com/web/testJSONP?callback=jQuery111109358763051531354_1595324608793&_=1595324608794
	 * 
	 * @return
	 * 注意事项:返回值结果必须经过特殊的封装   callback(JSON数据)
	 */
	@RequestMapping("/web/testJSONP")
	public JSONPObject jsonp(String callback) {
		//准备返回数据
		User user =new User();
		user.setId(100L).setPassword("我是密码");
		return new JSONPObject(callback,user);
	}
}

