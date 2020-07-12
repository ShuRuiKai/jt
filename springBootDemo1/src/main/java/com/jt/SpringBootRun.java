package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //2.springBoot最为重要的入口注解
//@mapperScan("com.jt.mapper")
public class SpringBootRun {
	/**
	 * 程序执行的过程:
	 * 	1.利用main方法执行springboot启动程序.
	 *  2.程序加载@SpringBootApplication
	 * 	  @Target(ElementType.TYPE) 当前注解对类有效  
		  @Retention(RetentionPolicy.RUNTIME)  在运行期改注解有效
          @Documented				相关文档信息
          @Inherited				可以被继承
          ---   上述的注解是元注解
		  
		  @SpringBootConfiguration  springBoot的主启动类就是一个配置类
		  @EnableAutoConfiguration	
		   		开箱即用:只要导入指定的jar包文件,则可以自动的实现配置!!!
		  		@AutoConfigurationPackage    //定义包扫描的路径信息  主启动类的包路径
		  									 以后写代码至少同包及子包中.
				@Import(AutoConfigurationImportSelector.class)  实现了开箱即用的功能
				总结: springBoot中选择器的配置.扫描pom.xml文件中的jar包文件.
					  之后将jar包文件中的配置予以执行.
				
					AutoConfigurationImportSelector implements 
						DeferredImportSelector, BeanClassLoaderAware,
						ResourceLoaderAware, BeanFactoryAware, 
						EnvironmentAware, Ordered
					说明: Aware是Spring框架从项目启动-到项目的效果的各个时期的接口.
				
				
		   了解: 加载第三方的类信息
		  @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		  @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRun.class, args);
	}

}
