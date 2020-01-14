package com.cyb.cache.demo.ehcache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印日志缓存事件监听程序
 * 
 * @author 01373660
 *
 */
public class LoggingCacheEventListener implements
		CacheEventListener<Object, Object> {

	/**
	 * 日志接口
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(LoggingCacheEventListener.class);

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
		logger.debug(
				"cache event, type = {}, key = {}, old value = {}, new value = {}",
				event.getType(), event.getKey(), event.getOldValue(),
				event.getNewValue());
	}

}
