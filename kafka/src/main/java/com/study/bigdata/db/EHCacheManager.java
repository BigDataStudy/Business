package com.study.bigdata.db;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;


public class EHCacheManager {
	private Logger log = Logger.getLogger(this.getClass().getSimpleName());

	private static EHCacheManager INSTANCE;

	private CacheManager manager;

	private EHCacheManager() {
		try {
			manager = new CacheManager(EHCacheManager.class.getClassLoader()
					.getResourceAsStream("ehcache.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("initialize eh cache error", e);
		}

	}

	public static EHCacheManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EHCacheManager();
		}
		return INSTANCE;

	}

	public Cache getCache(String name) {
		return manager.getCache(name);
	}

	
}
