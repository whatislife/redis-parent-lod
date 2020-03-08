package com.zhht.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Logger logger = LoggerFactory.getLogger(JedisJunit.class);
	/**
	 * 
	* <p>Title: initLogger  </p>
	* Description: 日志文件添加 parent中日志和实现 
	* @author  frankman
	* @date 2020年3月8日 下午7:47:17
	 */
	@Test
	public void initLogger(){
		logger.info("{} is {}", "logger","完美");
	}
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
