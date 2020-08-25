package com.jt.util;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//定义文件上传类型的信息
@Component //一般用来标识该类交给spring容器管理,不是任何业务层
@PropertySource("classpath:/properties/images.properties")
public class ImageTypeUtil {
	
	//可以利用spring容器动态为属性赋值
	@Value("${image.imageTypes}")
	private String imageTypes;		//类型1,类型2...
	private Set<String> typeSet=new HashSet<String>();
	
	//初始化集合信息
	//@PreDestroy		//当对象交给容器管理之后,执行该方法
	@PostConstruct		//当对象交给管理器之后,执行该方法
	public void init() {
		String[] typeArray=imageTypes.split(",");
		for (String type : typeArray) {
			typeSet.add(type);
		}
		//遍历完成之后,typeSet集合类型中有值的
		System.out.println("~~~set集合初始化完成~~~"+typeSet);
	}
	
	public Set<String> getTypeSet(){
		return typeSet;
	}
	
	
	
	
	private static Set<String> typeSet1 = new HashSet<String>();
	
	/**
	 * 一般图片类型就是常见几种,变化范围不大暂时可以写死
	 * 如果不想写死,通过IO流,读取指定配置文件进行封装
	 */
	static {
		typeSet1.add(".jpg");
		typeSet1.add(".png");
		typeSet1.add(".gif");
		typeSet1.add(".jpeg");
		typeSet1.add(".bmp");
	}
	public static Set<String> getImageType(){
		
		return typeSet1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
