package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

public interface ItemCatService {

	ItemCat findItemCatById(Long itemCatId);

	List<EasyUITree> findItemCatByParentId(Long parentId);

	/**
	 * 通过缓存的方式查询数据库.
	 * 1).定义key
	 * 2).根据key查询redis.
	 */
	List<EasyUITree> findItemCatByCache(Long parentId);

	

	

}
