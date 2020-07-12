package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
/**
 * 说明:
 * 	1. @Controller  + 返回值 String  表示跳转页面   必然经过视图解析器  拼接前缀和后缀
 *  2. @RestController +任意返回值   直接返回JSON数据,不经过视图解析器.
 *      或者  
 *     @Controller + @ResponseBody 
 */
@Controller
public class UserController {

	//JSON字符串   长得和js对象一致  不用自己手动转化js对象 user.id
	//(JSON字符串格式的)text文本----  字符串手动转化为js对象
	/**
	 * 查询user表的记录
	 * url地址: localhost/findAll
	 * jsp中页面取值信息 : ${userList}  同步查询
	 */
	@Autowired
	private UserMapper userMapper;
	@RequestMapping("/findAll")
	public String findAll(Model model) {
		List<User> userList = userMapper.selectList(null);
		model.addAttribute("userList",userList);
		return "userList";
		//WEB-INF/userList.jsp  拼接真实路劲
	};
	/**
	 * 实现页面跳转
	 */
	@RequestMapping("/ajax")
	public String ajax() {
		return "ajax";
	};
	
	//利用ajax形式获取数据
	@RequestMapping("/findAjax")
	@ResponseBody	//将返回值结果转化为JSON
	public List<User> findAjax(Model model){
		List<User> userList = userMapper.selectList(null);
		
		return userList;
	}
}
