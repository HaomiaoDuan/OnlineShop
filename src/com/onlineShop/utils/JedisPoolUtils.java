package com.onlineShop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
	
	private static JedisPool pool = null;
	
	static{
		//初始化配置，执行一次就行
		//1. 加载配置文件【重要!!】
		InputStream in = JedisPoolUtils.class.getClassLoader().getResourceAsStream("redis.properties");	//通过class文件获得src路径，找到配置文件
		Properties pro = new Properties();	//新建Properties对象
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//2.获得池子对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//properties对象的get(String)方法返回的是Object对象，需要转型
		poolConfig.setMaxIdle(Integer.parseInt(pro.get("redis.maxIdle").toString()));		//最大的闲置个数
		poolConfig.setMinIdle(Integer.parseInt(pro.get("redis.minIdle").toString()));		//最小的闲置个数
		poolConfig.setMaxTotal(Integer.parseInt(pro.get("redis.maxTotal").toString()));		//最大连接数
		
		/*JedisPool pool = new JedisPool(poolConfig, pro.getProperty("redis.url").toString(),  
				Integer.parseInt(pro.get("redis.port").toString()));						//【错误写法，难以看出！！】*/
		pool = new JedisPool(poolConfig, pro.getProperty("redis.url").toString(),  
				Integer.parseInt(pro.get("redis.port").toString()));
		
	}
	
	//3. 获得Jedis资源的方法
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	//测试
	public static void main(String[] args) {
		Jedis jedis = JedisPoolUtils.getJedis();
		System.out.println(jedis.get("xxx"));
	}
}
 