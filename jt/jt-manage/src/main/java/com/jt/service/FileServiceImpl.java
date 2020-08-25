package com.jt.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.util.ImageTypeUtil;
import com.jt.vo.ImageVO;
@Service
//指定配置文件,进行属性的注入 将key-value交给spring容器管理
@PropertySource("classpath:/properties/images.properties")
public class FileServiceImpl implements FileService {
	@Value("${image.localDir}")
	private String localDir;		//优化点一			//D:\\java\\LX
	@Value("${image.imageUrl}")
	private String imageUrl;		//定义url虚拟网址.
	@Autowired
	private ImageTypeUtil imageTypeUtil;
	//bug:	漏洞--一般情况没错       特殊情况：传递特殊参数时报错
	//error:错误
	
	/**
	 * 1.如何校验上传的信息是图片
	 * 		通过后缀进行校验：  	。jpg,	.png,	.gif,	jpge......
	 * 2.如何保证检索的速度更快
	 * 		分目录储存			1).hash		2).时间
	 * 3.如何防止文件重名
	 * 		重定义文件名		uuid.jpg
	 */
	@Override
	public ImageVO uploadFile(MultipartFile uploadFile) {
		//校验上传的信息 是否为图片
		//1.1初始化图片集合类型- - ->优化方式1、利用静态代码块方式实现。
		//Set<String>  typeSet = imageTypeUtil.getImageType();
		//- - ->优化方式2、利用spring方式进行优化
		Set<String> typeSet =imageTypeUtil.getTypeSet();	//可优化点二- - ->静态代码块
		
		//1.2动态获取用户上传的图片类型
		String fileName = uploadFile.getOriginalFilename();
		fileName = fileName.toLowerCase();	//将所有的字符转化为小写
		//动态获取.后面的  - - ->  .jpg
		int index = fileName.lastIndexOf(".");
		String fileType = fileName.substring(index);
		//1.2校验图片类型是否有效
		if(!typeSet.contains(fileType)) {
			//表示类型不属于图片信息  则终止程序
			return ImageVO.fail();
		}
		
		//2.准备文件上传的目录结构 	文件上传根目录+动态变化目录
		String dataDir =new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
		//D:\java\LX  +  2020/7/10
		String dirPath = localDir+dataDir;
		File dirFile = new File(dirPath);
		if(!dirFile.exists()) {
			dirFile.mkdirs();  //如果目录不存在则新建目录
		}
		//3.重新指定文件名称
		String uuid= UUID.randomUUID().toString();
		String realfileName=uuid+fileType;
		//4.执行文件上传代码			//目录+文件名称
		File imageFile =new File(dirPath+realfileName);
		try {
			uploadFile.transferTo(imageFile);		//优化点三
			//图片访问的虚拟路径 
			String url =imageUrl+dataDir+realfileName;
			return ImageVO.success(url);
		} catch (IllegalStateException|IOException e) {
			e.printStackTrace();
			return ImageVO.fail();
		}
	}
}
