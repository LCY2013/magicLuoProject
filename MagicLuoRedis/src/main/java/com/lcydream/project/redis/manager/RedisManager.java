package com.lcydream.project.redis.manager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisManager
 * redis管理工具
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/15 10:52
 */
public class RedisManager {

	/**
	 * redis连接池
	 */
	private static JedisPool jedisPool;

	static {
		//初始化连接池
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(20);
		jedisPoolConfig.setMaxIdle(20);
		jedisPool = new JedisPool(jedisPoolConfig,"192.168.21.160",6379);
	}

	/**
	 * 获取redis客户端
	 * @return redis客户端
	 */
	public static Jedis getJedisClient(){
		if(null != jedisPool){
			return jedisPool.getResource();
		}
		throw new RuntimeException("init redisPool fail or has not resource in this pool");
	}
}
