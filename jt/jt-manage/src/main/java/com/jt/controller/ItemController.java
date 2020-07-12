package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController //返回数据不需要经过视图解析器.所以不用Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	/**
	 * 业务:展现商品列表数据,一EasyUI表格数据展现
	 * url地址:http://localhost:8091/item/query?page=1&rows=20
	 * 参数:page=1&rows=20
	 * 返回值:EasyUITable  VO对象
	 */
	@RequestMapping("/query")		//所有的请求类型都可以接受
	public EasyUITable findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	
	
	/**
	 * 业务:商品的新增操作
	 * url:/item/save
	 * 参数:from表单数据
	 * 返回值结果:SysResult对象
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		
		//两张表同时入库
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
		//定义全局异常处理机制!!!
	}
//		try {
//			itemService.saveItem(item);
//			return SysResult.success();
//		}catch(Exception e) {
//			e.printStackTrace();	//打印错误信息
//			return SysResult.fail();
//		}
	
	/**
	 * 业务:商品的修改操作
	 * url:/item/update
	 * 参数:from表单数据
	 * 返回值结果:SysResult对象
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {

		itemService.updateItem(item,itemDesc);
		return SysResult.success();
		
	}
	
		/**
		 * 补充:	SpringMVC底层实现Servlet,数据传输协议https/http  一般船体的数据都是String结构.
		 * 		如果遇到重名的属性提交,浏览器会自己解析将value进行拼接 - - -> sex="男,男,男"
		 * 
		 * SpringMVC优化:
		 * 				1.可以自动的根据参数类型进行转化   String转化为具体类型.
		 * 				2.如果遇到了重名属性提交,则会自动转化为数组类型接收.
		 * 业务:商品的删除操作
		 * url:http://localhost:8091/item/delete
		 * 参数:from表单数据
		 * 返回值结果:SysResult对象
		 */
		@RequestMapping("/delete")
		public SysResult deleteItem(Long... ids) {
			itemService.deleteItem(ids);
			return SysResult.success();
			
		}
	
		/**
		 * 业务:商品的上架/下架操作
		 * url:/item/reshelf(instock)
		 * 参数:from表单数据Long
		 * 返回值结果:SysResult对象
		 */
//		@RequestMapping("/updatestatus/1") //上架
//		public SysResult reshelfItem(Long[] ids) {
//			itemService.reshelfItem(ids);
//			return SysResult.success();
//			
//		}
//		@RequestMapping("/updatestatus/2")  //下架
//		public SysResult instockItem(Long[] ids) {
//			itemService.instockItem(ids);
//			return SysResult.success();
//		}
		@RequestMapping("/updatestatus/{status}")  //合并1
		public SysResult updatestatus(Long[] ids,@PathVariable Integer status) {
			
			itemService.updatestatus(ids,status);
			return SysResult.success();
		}
		@RequestMapping("/status")					//合并2
		public SysResult statusItem(Long[] ids) {
			itemService.statusItem(ids);
			return SysResult.success();
		}
		/**
		 * 业务需求:  根据itemId查询商品详情信息
		 * url地址:  http://localhost:8091/item/query/item/desc/1474391972
		 * 参数:		使用restFul方式使用传输传递
		 * 返回值:    SysResult对象,并且需要携带itemDesc数据信息.
		 */
		@RequestMapping("query/item/desc/{itemId}")
		public SysResult findItemDescById(@PathVariable Long itemId) {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.success(itemDesc);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}