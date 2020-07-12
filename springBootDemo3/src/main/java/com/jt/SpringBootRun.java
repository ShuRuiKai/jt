package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//利用包扫描的形式 扫描指定包路径为接口创建代理对象 批量操作
@MapperScan("com.jt.mapper")  //@mapper的包扫描类型
public class SpringBootRun {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootRun.class, args);
	}

}