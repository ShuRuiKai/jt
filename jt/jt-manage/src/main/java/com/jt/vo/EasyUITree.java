package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor	
@AllArgsConstructor
@Accessors(chain = true)   //开启链式加载结构
public class EasyUITree {
	//{"id":"1","text":"英雄联盟","state":"open",}
	private Long id;		//id值  		与ItemCat中的Id一致的
	private String text;	//文本信息  	与ItemCat中的name属性一致
	private String state;	//状态   	打开:open		关闭:closed
	
}
