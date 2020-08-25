package com.jt.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestRedisShards {

	
	@SuppressWarnings("resource")
	@Test
	public void test01() {
		//1.准备list集合 之后添加节点信息
		List<JedisShardInfo> shards=new ArrayList<>();
		shards.add(new JedisShardInfo("192.168.126.129",6379));
		shards.add(new JedisShardInfo("192.168.126.129",6380));
		shards.add(new JedisShardInfo("192.168.126.129",6381));
		//2.创建分片对象
		ShardedJedis shardedJedis =new ShardedJedis(shards);
		shardedJedis.set("mima", "root");
		System.out.println(shardedJedis.get("mima"));
		
		
	}
}

