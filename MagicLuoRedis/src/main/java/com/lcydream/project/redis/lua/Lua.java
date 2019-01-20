package com.lcydream.project.redis.lua;

import com.lcydream.project.redis.manager.RedisManager;
import redis.clients.jedis.Jedis;

import java.util.LinkedList;

/**
 * Lua
 * 测试客户端调用lua脚本
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/15 20:24
 */
public class Lua {

	public static void main(String[] args) {
		//excutorOne();
		excutorTwo();
	}

	private static void excutorOne(){
		Jedis jedisClient = RedisManager.getJedisClient();
		String luaScript = "local num=redis.call('incr',KEYS[1])\n" +
				"if tonumber(num)==1 then\n" +
				"\tredis.call('expire',KEYS[1],ARGV[1])\n" +
				"\treturn 1\n" +
				"elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
				"\treturn 0\n" +
				"else\n" +
				"\treturn 1\n" +
				"end";
		LinkedList<String> keys = new LinkedList<>();
		keys.addFirst("ip:limit");
		LinkedList<String> argsList = new LinkedList<>();
		argsList.addLast("6000");
		argsList.addLast("10");
		for(int i=0;i<=10;i++) {
			Object eval = jedisClient.eval(luaScript, keys, argsList);
			System.out.println(eval);
		}
		jedisClient.close();
	}

	private static void excutorTwo(){
		Jedis jedisClient = RedisManager.getJedisClient();
		String luaScript = "local num=redis.call('incr',KEYS[1])\n" +
				"if tonumber(num)==1 then\n" +
				"\tredis.call('expire',KEYS[1],ARGV[1])\n" +
				"\treturn 1\n" +
				"elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
				"\treturn 0\n" +
				"else\n" +
				"\treturn 1\n" +
				"end";
		//执行一个load的加载，让你存在于redis缓存中，以后可以通过scriptLoad去直接调用
		//String scriptLoad = jedisClient.scriptLoad(luaScript);
		//System.out.println(scriptLoad);
		String scriptLoad = "a1ab36cffc066b25cfe780c8a960bfd4a89966bc";
		LinkedList<String> keys = new LinkedList<>();
		keys.addFirst("ip:limit:scriptLoad");
		LinkedList<String> argsList = new LinkedList<>();
		argsList.addLast("6000");
		argsList.addLast("10");
		for(int i=0;i<=10;i++) {
			Object eval = jedisClient.evalsha(scriptLoad, keys, argsList);
			System.out.println(eval);
		}
		jedisClient.close();
	}

}
