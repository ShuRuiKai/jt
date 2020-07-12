package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
// !!! POJO等实体类对象,一般都使用包装类型
@Data
@Accessors(chain = true)  //开启链式加载结构 重写了set方法 返回对象本身htis
@TableName("tb_item_cat") //关联商品分类表
public class ItemCat extends BasePojo{
	private static final long serialVersionUID = 5006550716849464495L;
	@TableId(type = IdType.AUTO)	//主键自增
	private Long id;
	private Long parentId;
	private String name;
	private Integer status;		//1.正常,2.删除
	private Integer sortOrder;	//排序号
	private Boolean isParent;	//0 false    !1真

}
