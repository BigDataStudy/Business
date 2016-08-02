package com.utils.redis;


import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import scala.Option;

public class Test {

	public static void main(String[] args) {
//		HashMap<Integer,Integer> m = new HashMap<Integer,Integer>();
//		for ( int i=0; i<10000; i++) {
//			int patition = UUIDPartitioner(UUID.randomUUID().toString().replace("-", ""), 6);
//			Integer v = m.get(patition);
//			if ( v == null ) {
//				v =1;
//			}else {
//				v = new Integer(v.intValue()+1);
//			}
//			m.put(patition, v);
//		}
//		
//		System.out.println(m);
		testJedis() ;
	}
	
	public static void  testJedis() {
		//Jedis jedis = new Jedis("9.115.65.48");
		Jedis jedis = new Jedis("9.110.86.120");
		jedis.set("foo", "barxxxx");
		String value = jedis.get("foo");
		System.out.println(value);
	}
	
	
	
	
	

}
