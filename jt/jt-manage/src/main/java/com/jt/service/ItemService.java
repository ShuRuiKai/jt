package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItemByPage(Integer page, Integer rows);

	void saveItem(Item item, ItemDesc itemDesc);

	void updateItem(Item item, ItemDesc itemDesc);

	void deleteItem(Long[] ids);

//	void reshelfItem(Long[] ids);

//	void instockItem(Long[] ids);

	void statusItem(Long[] ids);

	void updatestatus(Long[] ids, Integer status);

	ItemDesc findItemDescById(Long itemId);



	
}