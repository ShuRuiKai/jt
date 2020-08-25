package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jt.anno.CacheFind;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

//1.将对象交给容器管理
@Component
//2.定义AOP切面
@Aspect
public class CacheAOP {
	
	@Autowired(required=false)
	//private Jedis jedis;			//单台注入
	//private ShardedJedis jedis;	//多台注入
	private JedisCluster jedis;		////集群注入,可以实现高可用
	/**
	 * 实现思路: 拦截被@CacheFind标识的方法之后利用AOP进行缓存的控制
	 * 通知方法: 环绕通知
	 * 实现步骤:
	 * 		1.准备查询redis的key   ITEM_CAT_LIST::第一个参数
	 * 		2.("@annotation(cacheFind)")动态获取注解的语法.
	 * 		拦截指定注解类型的注解并且将注解对象当做参数进行传递.
	 */
	@SuppressWarnings("unchecked")
	//@Around("@annotation(com.jt.anno.CacheFind)")
	@Around("@annotation(cacheFind)")		//动态获取注解的语法
	public Object around(ProceedingJoinPoint joinPoint,CacheFind cacheFind) {
		//1.获取用户注解中的key   ITEM_CAT_LIST
		String key =cacheFind.key();
		
		//2.动态获取第一个参数当做key
		
		//joinPoint.getArgs()  获取目标方法的参数|[0]第一个参数Long parentId
		Object firstArg=joinPoint.getArgs()[0].toString();
		key +="::"+firstArg;
		Object result = null;
		
		//3.根据key查询redis
		if(jedis.exists(key)) {
			//根据redis获取信息
			String  json=jedis.get(key);
			MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();//getSignature得到方法对象的参数
			result = ObjectMapperUtil.toObject(json,methodSignature.getReturnType());
			System.out.println("aop查询redis缓存");
			
		}else {
			//如果key不存在,则证明是第一次查询,应该查询数据库
			try {
				result=joinPoint.proceed();
				System.out.println("aop查询数据库获取返回值");
				//将数据保存到redis中
				String json = ObjectMapperUtil.toJSON(result);
				int seconds=cacheFind.secondes();
				if(seconds>0)
				jedis.setex(key, seconds, json);
				else
				jedis.set(key, json);
			}catch(Throwable e){
				e.printStackTrace();
			}
		}	
		return result;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//公式:  切面=切入点表达式+通知方法.
	/**
	 * 业务需求:要求拦截ItemCatServiceImpl类中的业务方法
	 * @Pointcut 切入点表达式  可以理解为一个if判断,只有满足条件(在itemCatServiceImpl类中)才可以执行方法
	 */
	/**
//@Pointcut("bean(itemCatServiceImpl)")  //按类匹配,控制的力度较粗   单个bean
	//@Pointcut("within(com.jt.service.*)") //按类匹配,控制的力度较粗  多个bean
	 @Pointcut("execution(* com.jt.service..*.*(..))") //细粒度的匹配方式
	 @Pointcut("@annotation(com.jt.anno.CacheFind)")  //拦截注解  		//不需要获取注解里的内容用这个方法
	public void pointCut() {
	}
	//JoinPoint 
	@Before("pointCut()")		//前置通知(绑定切入点表达式)
	public void before(JoinPoint joinpoint) {
		System.out.println("前置通知(绑定切入点表达式)");
		String typeName=
				joinpoint.getSignature().getDeclaringTypeName();
		String methodName=joinpoint.getSignature().getName();
		System.out.println("方法的全路径为:"+typeName+"."+methodName);
		Object[] obj=joinpoint.getArgs();
		System.out.println("获取方法参数:"+obj);
		Object target =joinpoint.getTarget();
		System.out.println("获取目标对象:"+target);
	}
	//添加环绕通知
	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) {
		
		System.out.println("我是环绕通知开始");
		try {
			Object result = joinPoint.proceed();
			System.out.println("我是环绕通知结束");
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}*/
}
