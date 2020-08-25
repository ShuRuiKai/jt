package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service //(timeout = 10000)	//dubbo service注解	10秒超时 内部实现rpc
public class DubboCartServiceImpl implements DubboCartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);
	
		return cartMapper.selectList(queryWrapper);
	}
	@Override
	public void updateCartNum(Cart cart) {
		Cart cart1=new Cart();
		cart1.setNum(cart.getNum());
		
		
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<Cart>();
		
		updateWrapper.eq("item_id", cart.getItemId())
					 .eq("user_id", cart.getUserId());
		cartMapper.update(cart1, updateWrapper);
	}
	@Override
	public void saveCart(Cart cart) {
		//1.查询数据库中是否有该记录 itemId和userId
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("item_id", cart.getItemId())
					.eq("user_id", cart.getUserId());
		Cart cartDB =cartMapper.selectOne(queryWrapper);
		
		if(cartDB==null) {//说明是第一次
			cart.setCreated(new Date())
				.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {//只跟新商品数量
			int num =cart.getNum()+cartDB.getNum();

			//生成代码
//			Cart cart1 =new Cart();
//			cart1.setNum(cart.getNum())
//				.setId(cartDB.getId())
//				.setUpdated(new Date());
//			cartMapper.updateById(cart1);
			//手写sql代码
			cartMapper.updateCartNum(cartDB.getId(),num,new Date());
		}
	
	}
	@Override
	public void deleteCart(Cart cart) {
		//让对象中不为null的属性充当where条件
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);
	}

}
