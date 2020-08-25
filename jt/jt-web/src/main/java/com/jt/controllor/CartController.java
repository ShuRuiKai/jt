package com.jt.controllor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;

@Controller
@RequestMapping("/cart/")
public class CartController {

	@Reference(check = false)
	private DubboCartService cartService;
	
	@RequestMapping("show")
	public String show(Model model,HttpServletRequest request) {
		User user =(User) request.getAttribute("JT_USER");
	
		//1.获取userId	利用单点登入的方式获取userId   暂时写死
		Long userId =user.getId();
		//2.根据userId查询公务车数据
		List<Cart> cartList =cartService.findCartListByUserId(userId);
		//利用model对象将对象数据填充到域对象request域中
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	
	/**
	 * 业务需求: 完成购物车商品跟新操作
	 * 1.url:http://www.jt.com/cart/update/num/562379/9
	 * 2.请求参数:562379-itemId		9-num
	 * 3.返回值结果:void
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public void updateCart(Cart cart,HttpServletRequest request) {	//参数如果和属性名称一致则可以直接赋值.
		User user =(User) request.getAttribute("JT_USER");
		
		Long userId =user.getId();
		cart.setUserId(userId);
		cartService.updateCartNum(cart);
	}
	
	/**
	 * url:http://www.jt.com/cart/add/562379.html
	 * 参数:Cart
	 * 返回值:重定向到购物车页面
	 */
	@RequestMapping("add/{itemId}")
	public String saveCart(Cart cart,HttpServletRequest request) {
		User user =(User) request.getAttribute("JT_USER");
		
		Long userId =user.getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	/**
 * 业务:删除购物车操作
 * url地址: http://www.jt.com/cart/delete/562379.html
 * 参数问题: 562379
 * 返回值结果: 重定向到购物车列表页面
 */
@RequestMapping("delete/{itemId}")
public String deleteCart(@PathVariable Long itemId,HttpServletRequest request) {
	User user =(User) request.getAttribute("JT_USER");
	
	Long userId =user.getId();
	Cart cart = new Cart();
	cart.setUserId(userId);
	cart.setItemId(itemId);
	cartService.deleteCart(cart);
	return "redirect:/cart/show.html"; //维护伪静态策略
}



}

