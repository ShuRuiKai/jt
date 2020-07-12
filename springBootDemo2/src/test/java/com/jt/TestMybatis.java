package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@SpringBootTest	 //springBoot测试注解  开启了spring容器
public class TestMybatis {
	
	@Autowired
	private UserMapper userMapper;
	//以MP方式操作数据库,以后单表查询
	@Test
	public void findAll() {
		
		List<User> userList = userMapper.selectList(null);
		System.out.println(userList);
	}
	/**
	 * 新增
	 */
	@Test
	public void insert() {
		User user =new User();
		user.setName("小小黑")
			.setAge(15)
			.setSex("女");
		userMapper.insert(user);
		System.out.println("入库成功");
	}
	/**
	 * 查询API说明
	 * 案例：查询id=53的用户信息
	 */
	@Test
	public void select01() {
		User user =userMapper.selectById(53);
		System.out.println(user);
	}
	
	/**
	 * 查询API说明
	 * 案例：查询name="孙尚香" sex="女"  的用户
	 * queryWrapper:条件构造器  拼接where条件
	 * 逻辑运算符  ： =(eq),>(gt),<(lt),>=(ge),<=(le)
	 */
	@Test
	public void select02() {
		//1、利用对象封装参数，实现数据查询
		User user =new User();
		user.setName("孙尚香")
			.setSex("女");
		//根据对象中不为null的元素拼接where条件，默认条件下使用and连接符
		QueryWrapper<User> queryWrapper =new QueryWrapper<>(user);
		List<User> userList =userMapper.selectList(queryWrapper);
		System.out.println(userList);
		
		//2利用条件构造器构建where条件
		QueryWrapper<User> queryWrapper2 =new QueryWrapper<>(user);
		queryWrapper2.eq("name", "孙尚香")
					.eq("sex", "女");
		List<User> userList2 =userMapper.selectList(queryWrapper2);
		System.out.println(userList2);
	}
	/**
	 * 查询用户age<18岁  或者  age>100岁
	 */
	@Test
	public void select03() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lt("age", 18)
					.or()
					.gt("age", 100);
		List<User> userList =userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 查询id=1,3,6的用户
	 */
	@Test
	public void select04() {
		//1、通过API进行封装
		List<Integer> list =new ArrayList<>();
		list.add(1);
		list.add(3);
		list.add(6);
		List<User> userList =userMapper.selectBatchIds(list);
		System.out.println(userList);
		//2、条件构造器进行封装
		QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
		queryWrapper.in("id", 1,3,6);
		List<User> userList1 = userMapper.selectList(queryWrapper);
		System.out.println(userList1);
	}
	
	@Test
	public void select05() {
		QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
		//queryWrapper.between("字段", "值1", "值2");
		//queryWrapper.like("name", "精");			//包含精字  %精%
		//queryWrapper.likeRight("name", "精");	//以精开头    精%
		//queryWrapper.likeLeft("name", "精");		//以精结尾  %精
		//queryWrapper.groupBy("name");			//分组查询
		
//		queryWrapper.between("age", "1", "100");
//		List<User> userList = userMapper.selectList(queryWrapper);
//		System.out.println(userList);
		
		queryWrapper.likeLeft("name", "精");		//以精结尾  %精
		List<User> userList1 = userMapper.selectList(queryWrapper);
		System.out.println(userList1);
		
	}
	
	
	
	
}