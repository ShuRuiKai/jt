package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	/**
	 * 分页的SQL:select xxxx,xxxx from tb_item limit 起始位置,查询记录数
	 * 查询第一页:select * from tb_item limit 0,20		(规则:含头不含尾特点)0-19共20个记录
	 * 查询第二页:select * from tb_item limit 20,20		(规则:含头不含尾特点)20-39共20个记录
	 * 查询第三页:select * from tb_item limit 40,20		(规则:含头不含尾特点)40-59共20个记录
	 * 等差数列第N项:	(n-1)*rows
	 */
//	@Override
//	public EasyUITable findItemByPage(Integer page, Integer rows) {
//		//1.获取记录总数
//		int total=itemMapper.selectCount(null);
//		
//		//2.查询分页后的结果
//		int start =(page-1)*rows;
//		List<Item> itemList=itemMapper.selectItemByPage(start,rows);
//		
//		return new EasyUITable(total, itemList);
//	}
	//利用Mp方式进行查询
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		//1.定义分页对象
		Page<Item> mpPage = new Page<>(page,rows);
		//2.定义条件构造器
		QueryWrapper<Item> queryWrapper =new QueryWrapper<Item>();
		//3.要求:按照更新时间,进行排序,降序排序
		queryWrapper.orderByDesc("updated");
		//执行分页操作,之后封装Page对象数据
		mpPage=itemMapper.selectPage(mpPage, queryWrapper);
		int total =(int)mpPage.getTotal();		//获取总记录数
		List<Item> items = mpPage.getRecords(); //获取当前分页记录
		return new EasyUITable(total, items);//(总数,分页记录数)
	}
	
	
	@Transactional  //控制数据库事务
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		//1.商品入库
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//由于主键自增的原因,所以程序入库之后才会有主键信息
		//MP提供业务支持实现数据回显功能!!!  入库操作之后,会将所有的数据库记录进行映射给对象
		//底层实现是Myabatis的主键自增自动回显功能
		
		//2.商品详情入库	
		itemDesc.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Transactional  //控制数据库事务
	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		//更新商品信息
		item.setCreated(new Date());
		itemMapper.updateById(item);
		
		//更新商品详情信息
		itemDesc.setItemId(item.getId())
				.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Transactional  //控制数据库事务
	@Override
	public void deleteItem(Long[] ids) {
		//删除商品
			//Arrays.asList(ids)- - ->将数组转化为集合
		List<Long> idList=Arrays.asList(ids);
			itemMapper.deleteBatchIds(Arrays.asList(ids));
		//删除商品详情信息
			itemDescMapper.deleteBatchIds(idList);
	}

//	@Transactional  //控制数据库事务
//	@Override
//	public void reshelfItem(Long[] ids) {
//		Item item=new Item();
//		item.setStatus(1)
//			.setUpdated(new Date());
//		//定义条件构造器
//		UpdateWrapper<Item> updateWrapper =new UpdateWrapper<>();
//		updateWrapper.in("id", Arrays.asList(ids));
//		itemMapper.update(item, updateWrapper);
//	}
//
//	@Transactional  //控制数据库事务
//	@Override
//	public void instockItem(Long[] ids) {
//		Item item=new Item();
//		item.setStatus(2)
//			.setUpdated(new Date());	//设置状态和更新时间
//		//定义条件构造器
//		UpdateWrapper<Item> updateWrapper =new UpdateWrapper<>();
//		updateWrapper.in("id", Arrays.asList(ids));
//		itemMapper.update(item, updateWrapper);	//传入所有选中的id
//	}
	
	
	/**
	 * MP的更新操作
	 * 1.entity	 要修改的记录
	 * 2.updateWrapper	要修改条件构造器
	 */
	@Override
	@Transactional  //控制数据库事务
	public void updatestatus(Long[] ids, Integer status) {
		//1.定义修改数据
		Item item=new Item();
		item.setStatus(status).setUpdated(new Date());
		//2.定义修改条件
		List<Long> idList = Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper =new UpdateWrapper<>();
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
	}
	
	
	
	@Override
	public void statusItem(Long[] ids) {
		
		Item item=new Item();
		Integer status;
		for (Long id : ids) {
			item= itemMapper.selectById(id);
			status=item.getStatus();
			//Item item=new Item();
			status=status==1?2:1;										//status=1则改为2,,!=1则改为1
			item.setStatus(status).setUpdated(new Date());				//设置状态和更新时间
			itemMapper.updateById(item);
		}
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}





	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}