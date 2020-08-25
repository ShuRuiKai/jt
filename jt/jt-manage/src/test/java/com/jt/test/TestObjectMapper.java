package com.jt.test;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

public class TestObjectMapper {

	private static final ObjectMapper Mapper = new ObjectMapper();
	/**
	 * 目的: 实现对象与json串之间的转化
	 * 步骤1:  将对象转化为json
	 * 步骤2:  将json转化为对象 
	 * 利用ObjectMapper 工具API实现
	 * @throws JsonProcessingException 
	 */
	@Test
	public void test01() throws JsonProcessingException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(101L).setItemDesc("json转化测试")
				.setCreated(new Date()).setUpdated(itemDesc.getCreated());
		//1.将对象转化为JSON   调用的是对象的get方法
		String json=Mapper.writeValueAsString(itemDesc);
		System.out.println(json);
		
		//2.将json转化为对象   传递需要转化之后的class类型   调用是对象的set方法
		ItemDesc itemDesc2 = Mapper.readValue(json, ItemDesc.class);
		System.out.println(itemDesc2.getItemId());
	}
}
