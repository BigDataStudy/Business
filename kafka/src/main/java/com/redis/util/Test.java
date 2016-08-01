package com.redis.util;

import redis.clients.jedis.Jedis;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("9.115.65.48");
		jedis.set("foo", "barxxxx");
		String value = jedis.get("foo");
		System.out.println(value);
	}

}
