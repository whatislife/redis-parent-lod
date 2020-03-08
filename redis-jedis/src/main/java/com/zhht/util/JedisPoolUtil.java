package com.zhht.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
* @ClassName: JedisPoolUtil  
* <p>Description: jedisPool运用单例模式 双重检查加锁 
*   单例模式中会学到双重检查加锁   然后volatile关键词会被问
*   https://my.oschina.net/yukong/blog/870890
*   jedisPoolConfig参数 
*   https://www.cnblogs.com/zdj-/p/8677314.html
*   https://www.cnblogs.com/aflyun/p/11708003.html
*   
*  </p>
* @author  frankman
* @date 2020年3月6日 下午4:38:29  
*
 */
public class JedisPoolUtil {
	private static volatile JedisPool jedisPool = null;
	public JedisPoolUtil() {
		
	}
	@SuppressWarnings("unused")
	public static JedisPool getJedisPoolInstance(){
		if(null == jedisPool){
			synchronized(JedisPoolUtil.class){
				if(null == jedisPool){
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxTotal(10);
	                poolConfig.setMaxIdle(10);
	                poolConfig.setMinIdle(2);
	                poolConfig.setMaxWaitMillis(30*1000);
	                poolConfig.setTestOnBorrow(false);
	                poolConfig.setTestOnReturn(false);
	                poolConfig.setTimeBetweenEvictionRunsMillis(10*1000);
	                poolConfig.setMinEvictableIdleTimeMillis(30*1000);
	                poolConfig.setNumTestsPerEvictionRun(-1);
					jedisPool = new JedisPool(poolConfig, "192.168.199.131", 6379,10000,"123456");
				}
			}
		}
		return jedisPool;
	}
	@SuppressWarnings("unused")
	public static void release(JedisPool jedisPool , Jedis jedis){
		jedisPool.close();
	}

}
