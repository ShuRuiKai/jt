package com.jt.controllor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user/")
public class UserControllor {
	
	//启动消费者时,不需要检查是否有服务提供者
	@Reference(check= false)
	private DubboUserService userService;
	@Autowired
	public JedisCluster jedisCluster;


	/**
	 * 实现用户页面跳转
	 * http://www.jt.com/user/register.html		//register.jps
	 * http://www.jt.com/user/login.html		//login.jps
	 * 重点为了实现业务功能,拦截.html结尾的请求
	 */
	@RequestMapping("register")
	public String register() {
		return "register";
	}
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	/**
	 * 完成用户注册
	 * 1.url地址：  http://www.jt.com/user/doRegister
	 * 2.参数：         用户名/密码/电话号码
	 * 3.返回值：    SysResult对象
	 */
	@RequestMapping("doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		
		userService.saveUser(user);
		return SysResult.success();
	}

	/**
	 * 完成用户登录
	 * 1.url地址：  http://www.jt.com/user/doLogin
	 * 2.参数：       username/password
	 * 3.返回值：    SysResult对象
	 * 关于Cookie:
	 * 	Domain:指定地域名可以实现Cookie数据的共享
	 *  Path: 只有在特定路径下,才能获取Cookie.
	 *  	网址:www.jd.com/abc/findAll
	 *  		cookie.setPath("/aa");	只允许/aa的路径获取该Cookie信息.
	 *  		cookie.setPath("/");	任意网址,都可以获取Cookie信息.
	 */
	@ResponseBody
	@RequestMapping("doLogin")
	public SysResult doLogin(User user,HttpServletResponse response) {
		//1.通过user传递用户名和密码,交给业务层service进行校验,获取ticket信息(校验之后回执)
		String ticket=userService.doLogin(user);
		
		if(StringUtils.isEmpty(ticket)) {
			//证明用户名或密码错误.
			return SysResult.fail();
		}
		
		//2.准备Cookie实现数据存储
		Cookie cookie = new Cookie("JT_TICKET", ticket);
		cookie.setDomain("jt.com");   //jt.com结尾的可以域名,实现Cookie数据的共享
		cookie.setPath("/");
		cookie.setMaxAge(7*24*60*60);//7天超时
		//将Cookie保存到客户端
		response.addCookie(cookie);
		
		return SysResult.success();
	}

	/**
	 * 用户退出:
	 * url: http://www.jt.com/user/logout.html
	 * 没有传递参数
	 * 返回值:String		重定向返回系统首页 
	 * 业务实现思路:
	 * 		先获取cookie中的数据 NAME=JT_TICKET
	 * 		1.删除redis中数据     key-value		key=cookie中的value
	 * 		2.删除Cookie记录 	根据cookie名称	设置存活时间即可
	 * 
	 * 注意事项:request对象中只能传递cookie的那么和value.不能传递其他数据参数
	 */
		@RequestMapping("logout")
		public String logout(HttpServletRequest request,HttpServletResponse response) {

			Cookie[] cookies = request.getCookies();//获取所有的cookie
			if(cookies !=null && cookies.length>0) {//如果cookie为0或长度不大于0则直接返回重定向到首页
				for (Cookie cookie : cookies) {//遍历cookie
					if("JT_TICKET".equalsIgnoreCase(cookie.getName())) {//找到key为JT_TICKET的cookie
						String ticket =cookie.getValue();
						//删除redis
						jedisCluster.del(ticket);
						//删除cookie
						cookie.setDomain("jt.com");   //jt.com结尾的可以域名,实现Cookie数据的共享
						cookie.setPath("/");
						cookie.setMaxAge(0);//立即删除:0/暂时不删,关闭浏览器删除:-0;
						//将Cookie保存到客户端
						response.addCookie(cookie);
						break;
					}
				}
			}
			//转发forward:/
			return "redirect:/";//重定向到系统首页
		}

}
