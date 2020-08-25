package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration //我是一个配置类  一般都会与@Bean联用
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	
	@Value("${redis.nodes}")
	private String RedisNodes;	//nodie1,node2,node3
	/*整合分片实现Redis内存扩容*/
	@Bean
	public JedisCluster shardedJedis() {
		//创建动态获取Redis节点信息
		//List<JedisShardInfo> list =new ArrayList<JedisShardInfo>();
		Set<HostAndPort> set = new HashSet<HostAndPort>();
		String[] nodes =RedisNodes.split(","); //将数据分割成String数组nodie1,node2,node3
		for (String node : nodes) {//node= host:port ---->[host,port]
			String host=node.split(":")[0];
			Integer port=Integer.parseInt(node.split(":")[1]);
			
			//list.add(new JedisShardInfo(host,port));
			set.add(new HostAndPort(host,port));//将[host,port]封装到list中
		}
		
		return new JedisCluster(set);
	}
	
	

	
	
	
/**
	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private Integer port;
	//将返回值的结果交给spring容器管理,如果以后想要使用该对象则可以直接注入
	@Bean
	public Jedis jedis() {
		
		return new Jedis(host,port);
	}
	*/
}

