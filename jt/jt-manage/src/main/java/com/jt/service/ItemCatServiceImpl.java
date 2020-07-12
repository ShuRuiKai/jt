package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public ItemCat findItemCatById(Long itemCatId) {
//	QueryWrapper<ItemCat> queryWrapper =new QueryWrapper<ItemCat>();
//	queryWrapper.select("name,id");
//	itemCatMapper.selectByMaps  (queryWrapper);
		return itemCatMapper.selectById(itemCatId);
	}

	/**
	 * 数据的来源:数据库中
	 * 数据库中的数据类型:ItemCat对象信息
	 * 需要的类型:		EasyUITree对象信息,VO对象
	 * 思路:将ItemCat转化为EasyUITree对象.
	 * sql: SELECT * FROM tb_item_cat WHERE parent_id=#{parent_id}
	 */
	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		//1.根据parentId查询数据库信息   根据父级查询子级信息.
		QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
		 
		//2.将数据库记录转化为VO数据
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();		//创建一个空集合用于返回数据
		for (ItemCat itemCat : itemCatList) {
			Long id=itemCat.getId();
			String text=itemCat.getName();
			//如果是父级标题,则默认关闭closed,否则开启,open
			String state=itemCat.getIsParent()?"closed":"open";
			EasyUITree uiTree =new EasyUITree(id,text,state);
			treeList.add(uiTree);
		}
		
		
		return treeList;
	}
	
}
