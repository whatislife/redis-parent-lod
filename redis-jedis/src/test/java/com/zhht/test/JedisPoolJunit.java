package com.zhht.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zhht.util.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
/**
 * 
* @ClassName: JedisJunit  
* <p>Description: redis单元测试  </p>
* @author  frankman
* @date 2020年3月6日 下午4:18:24  
*
 */
public class JedisPoolJunit {
	private JedisPool jedisPool ; 
	private Jedis jedis;
	@Before
	public void init() throws Exception {
		System.out.println("init()");
		jedisPool = JedisPoolUtil.getJedisPoolInstance();
		jedis = jedisPool.getResource();
	}

	@After
	public void destroy() throws Exception {
		System.out.println("destroy()");
		JedisPoolUtil.release(jedisPool, jedis);
	}
	public void jedisPing(){
		System.out.println("jedis ping .... " + jedis.ping());
	}
	@Test
	public void jedisString() {
		jedis.set("username", "frankman");
		System.out.println("jedis get ...."+jedis.get("username"));
	}
	@Test
	public void jedisNormal(){
		long start = System.currentTimeMillis();
		System.out.println("start..."+start);
		for(int i = 0 ; i < 10000 ; i ++){
			jedis.hset("pipelinek-"+i, "pipelinef-"+i,"pipelinev-"+i);
		}
		long end = System.currentTimeMillis();
		System.out.println("start 2 end..."+(end-start));
	}
	/**
	 * 
	* <p>Title: jedisPipeline  </p>
	* Description: 流水线方式比传统方式 快很多 主要是pipeline减少了网络交互时间批量汇总处理
	* @author  frankman
	* @date 2020年3月6日 下午8:25:55
	 */
	@Test
	public void jedisPipeline(){
		long start = System.currentTimeMillis();
		System.out.println("start..."+start);
		for(int i = 0 ; i < 100 ; i ++){
			Pipeline line = jedis.pipelined();
			for(int j = i*100;j<(i+1)*100;j++){
				line.hset("pipelinek-"+j, "pipelinef-"+j,"pipelinev-"+j);
			}
			line.syncAndReturnAll();
		}
		long end = System.currentTimeMillis();
		System.out.println("start 2 end..."+(end-start));
	}
}
