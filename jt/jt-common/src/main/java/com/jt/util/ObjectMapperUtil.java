package com.jt.util;

import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	//json与对象转化   异常处理优化
	private static final ObjectMapper Mapper = new ObjectMapper();
	
	public static String toJSON(Object target) {
		
		if(target==null) {
			throw new NullPointerException("taget数据为空");
		}
		try {
			return Mapper.writeValueAsString(target);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);  //如果转化过程中有问题则直接抛出异常
		}
	}
	
	//用户传递什么样地类型,就返回什么样的对象!!!
	//<T>  定义一个泛型对象  代表任意类型
	public static <T> T toObject(String json,Class<T> targetClass) {
		
		if(StringUtils.isEmpty(json)||targetClass==null) {
			throw new NullPointerException("参数不能为空");
		}
		try {
			return Mapper.readValue(json,targetClass);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);  //如果转化过程中有问题则直接抛出异常
		}
	}
}
