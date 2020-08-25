package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Accessors(chain = true)
@Data
@TableName("tb_cart")
public class Cart extends BasePojo{

	@TableId(type = IdType.AUTO)
	private Long id;			//id
	private Long userId;		//用户
	private Long itemId;		//商品
	private String itemTitle;	//标题
	private String itemImage;	//主图
	private Long itemPrice;		//价格
	private Integer num;			//购买数量

}
