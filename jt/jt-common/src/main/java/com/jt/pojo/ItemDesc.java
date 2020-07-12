package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) 	//开启链式加载结构
@TableName("tb_item_desc") 	//表名
public class ItemDesc extends BasePojo{
	@TableId						//主键
	private Long  itemId;           //'商品ID', 既是item表的主键,也是itemDesc表的主键值相同
	private String  itemDesc;       // 商品详情信息
}