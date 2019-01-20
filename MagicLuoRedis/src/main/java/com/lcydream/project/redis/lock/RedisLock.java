package com.lcydream.project.redis.lock;

import com.lcydream.project.redis.manager.RedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisLock
 * redis实现分布式锁
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/15 11:07
 */
public class RedisLock {

	/**
	 *  获取锁资源
	 * @param key 锁对象
	 * @param timeout 等待的超时时间，单位ms,默认3000ms
	 * @return 获取是否成功，成功返回{@code String}UUID,失败返回{@code null}
	 */
	public String getLock(String key,Long timeout){
		//jedis客户端
		Jedis jedisClient = null;
		try {
			jedisClient = RedisManager.getJedisClient();
			String value = UUID.randomUUID().toString();
			//默认值给一个3毫秒
			if(timeout == null){
				timeout = 3000L;
			}
			//计算出一个结束时间
			long endTime = System.currentTimeMillis()+timeout;
			while (endTime > System.currentTimeMillis()) {
				//设置key成功
				if (1L == jedisClient.setnx(key, value)) {
					//设置key的过期时间
					jedisClient.expire(key,(int)(timeout/1000==0?1:timeout/1000));
					return value;
				}
				//如果在设置key成功后redis连接失败，我们就需要在这里判断是否设置了过期时间
				if(-1 == jedisClient.ttl(key)){
					//设置key的过期时间
					jedisClient.expire(key,(int)(timeout/1000==0?1:timeout/1000));
				}
				if(timeout < 3000) {
					//给休眠分成5段执行
					TimeUnit.MILLISECONDS.sleep(timeout / 5);
				}else if(timeout < 10000){
					//给休眠分成10段执行
					TimeUnit.MILLISECONDS.sleep(timeout / 10);
				}else {
					TimeUnit.MILLISECONDS.sleep(1000);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(jedisClient != null){
				jedisClient.close();
			}
		}
		return null;
	}

	/**
	 * 释放锁
	 * @param key 锁字段
	 * @return 是否释放成功
	 */
	public boolean releaseLock(String key,String value){
		//jedis客户端
		Jedis jedisClient = null;
		//判断参数是否合法
		if(key == null || value == null){
			throw new RuntimeException("Error parameter");
		}
		try {
			jedisClient = RedisManager.getJedisClient();
			while (true) {
				//观察这个key的动态
				jedisClient.watch(key);
				if (value.equals(jedisClient.get(key))) {
					//开启redis事物
					Transaction multi = jedisClient.multi();
					//删除锁对象
					multi.del(key);
					//提交事物
					List<Object> exec = multi.exec();
					if(null == exec){
						continue;
					}
					return true;
				}
				//取消观察这个key
				jedisClient.unwatch();
				break;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(null == jedisClient){
				jedisClient.close();
			}
		}
		return false;
	}

	public static void main(String[] args) {
		final RedisLock redisLock = new RedisLock();
		final Random random = new Random();
		for(int i=0;i<10;i++){
			new Thread(()->{
				String lockValue=null;
				if(null != (lockValue = redisLock.getLock("order.id",1000L*random.nextInt(10)))){
					System.out.println(Thread.currentThread()+"-成功获取锁,id="+lockValue);
					boolean releaseLock = redisLock.releaseLock("order.id", lockValue);
					if(releaseLock){
						System.out.println(Thread.currentThread()+"-释放锁成功");
					}else {
						System.out.println(Thread.currentThread()+"-释放锁失败");
					}
				}else {
					System.out.println(Thread.currentThread()+"-获取锁失败");
				}
			}).start();
		}
	}
}
