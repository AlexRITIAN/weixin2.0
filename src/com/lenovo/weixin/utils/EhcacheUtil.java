package com.lenovo.weixin.utils;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;

public class EhcacheUtil {

	// private static final String path = "/ehcache.xml";
	// private static UserManagedCache<String, String> userManagerCache =
	// UserManagedCacheBuilder
	// .newUserManagedCacheBuilder(String.class, String.class).build(true);

	private static volatile EhcacheUtil cache;
	private final UserManagedCache<String, String> userManagerCache;

	private EhcacheUtil() {
		userManagerCache = UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, String.class).build(true);
	}

	public static EhcacheUtil getEhcacheUtil() {
		if (cache == null) {
			synchronized (EhcacheUtil.class) {
				if (cache == null) {
					cache = new EhcacheUtil();
				}
			}
		}
		return cache;
	}

	public void putCache(String key, String value) {
		userManagerCache.put(key, value);
	}

	public String getCache(String key) {

		return userManagerCache.get(key);

	}

	public void clear() {
		userManagerCache.clear();
	}

	public void remove(String key) {
		userManagerCache.remove(key);
	}

}
