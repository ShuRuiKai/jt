package com.jt.test;

import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

public class TestRedis {
/**
 * 1.spring整合redis
 * 报错说明:
 * 	1).如果测试过程中报错 则检查redis配置文件  改3处
 *  2).检查redis启动方式   redis-server redis.conf
 *  3).检查Linux的防火墙
 */
	@Test
	public void test01() {
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		//2.操作redis
		jedis.set("a","reids入门案例");
		String value =jedis.get("a");
		System.out.println(value);
	}
	@Test
	public void test02() {
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		//2.判断当前数据是否存在
		if(jedis.exists("a")) {
			System.out.println(jedis.get("a"));
		}else {
			jedis.set("a", "测试是否存在");
		}
	}
	/**
	 * 1.能否简化是否存在的判断
	 * 2.如果该数据不存在时修改数据,否则不修改
	 * setnx:将key设置值为value，如果key不存在
	 */
	@Test
	public void test03() {
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		jedis.flushAll();//清空所有的redis缓存
		jedis.setnx("b", "123");
		jedis.setnx("b", "321");
		
	}
	/**
	 * 原子性:要么同时成功,要么同时失败
	 * @throws Exception
	 */
	@Test
	public void test04() throws Exception {
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		jedis.flushAll();//清空所有的redis缓存
		
		jedis.set("c", "666666");
		jedis.expire("c", 20);//添加超时时间
		Thread.sleep(2000);//睡2秒
		System.out.println("剩余存活时间:"+jedis.ttl("c"));
		
		//2.实现原子性操作
		jedis.setex("d", 20, "原子性测试");
		System.out.println(jedis.get("c"));
	}
	/**
	 * 1.只有数据不存在时允许修改
	 * 2.要求实现添加超时时间,并且是原子性操作
	 * SetParams参数说明:
	 *  1.NX	只有key不存在时才能修改
	 *  2.XX	只有key存在时才能修改
	 *  3.PX	添加的时间单位是毫秒
	 *  4.EX	添加的时间单位是秒
	 */
	@Test
	public void test05(){
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		jedis.flushAll();//清空所有的redis缓存
		
		SetParams params = new SetParams();
		params.nx().ex(20);
		jedis.set("aa", "aa123", params);
		jedis.set("aa", "aa456", params);
		System.out.println(jedis.get("aa"));
		
	}
	/**
	 * 存储一类数据,可以用hash
	 */
	@Test
	public void test06(){
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		jedis.flushAll();//清空所有的redis缓存
		jedis.hset("user", "name","skycloud");
		jedis.hset("user", "id","100");
		jedis.hset("user", "age","16");
		System.out.println(jedis.hgetAll("user"));
	}
	/**
	 * 存储一类数据,可以用list
	 */
	@Test
	public void test07(){
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		jedis.flushAll();//清空所有的redis缓存
		jedis.lpush("list", "1","2","3","4","5","6","7","8","9");	//左边插入
		jedis.rpushx("list", "666");								//如果有这个key,则右边插入
		//一个一个输出
		System.out.println(jedis.rpop("list"));
		System.out.println(jedis.rpop("list"));
	}
	//控制事务
	@Test
	public void test08(){
		//1.创建jedis对象
		Jedis jedis = new Jedis("192.168.126.129",6379);
		jedis.flushAll();//清空所有的redis缓存
		
		Transaction transaction = jedis.multi();		//开启事务
		try {
		transaction.set("aaa", "aaa");
		transaction.set("bbb", "bbbb");
		transaction.set("ccc", "ccccc");
		transaction.exec();			//事务提交
		}catch (Exception e){
		e.printStackTrace();
		transaction.discard();		//事务回滚
		}
	}
}
