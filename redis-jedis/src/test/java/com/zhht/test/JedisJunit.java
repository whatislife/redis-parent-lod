package com.zhht.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
/**
 * 
* @ClassName: JedisJunit  
* <p>Description: redis单元测试  </p>
* @author  frankman
* @date 2020年3月6日 下午4:18:24  
*
 */
public class JedisJunit {
	/*
	 * @Before public void setUp() throws Exception {
	 * System.out.println("setUp()"); //两个Before }
	 */
	private Jedis jedis;
	@Before
	public void init() throws Exception {
		System.out.println("init()");
		jedis = new Jedis("192.168.199.131", 6379);
		jedis.auth("123456");//密码
		//System.out.println(jedis.ping());
		jedis.connect();
	}

	@After
	public void destroy() throws Exception {
		System.out.println("destroy()");
		jedis.disconnect();
	}
	public void jedisPing(){
		System.out.println("jedis ping .... " + jedis.ping());
	}
	@Test
	public void jedisString() {
		jedis.set("username", "frankman");
		System.out.println("jedis get ...."+jedis.get("username"));
	}
}
