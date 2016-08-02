package com.utils.redis;


public class TestRedisQuene {
	public static byte[] redisKey = "key".getBytes();

	public static void main(String[] args) {
		
		/*for ( int i=0; i<10; i++ ) {
			byte[] value = String.valueOf("aa"+i).getBytes();
			JedisUtil.lpush("didi-1".getBytes(), value);
		}
		
		for ( int i=0; i<10; i++ ) {
			
			System.out.println(new String(JedisUtil.rpop("didi-1".getBytes())));
		}*/
		
		while(true) {
			System.out.println(new String(JedisUtil.rpop("didi-order".getBytes())));
		}
	}

}
