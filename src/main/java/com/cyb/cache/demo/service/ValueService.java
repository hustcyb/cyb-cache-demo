package com.cyb.cache.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 值服务
 * 
 * @author 01373660
 *
 */
@CacheConfig(cacheNames = "VAL")
@Service
public class ValueService {

	/**
	 * 日志接口
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ValueService.class);

	/**
	 * 数据字典
	 */
	private static final Map<String, String> values = new HashMap<>();

	@Cacheable(key = "#key")
	public String getValue(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("ValueController.getValue: start, key = {}", key);
		}

		String value = values.get(key);
		if (logger.isDebugEnabled()) {
			logger.debug("ValueController.getValue: end, return = {}", value);
		}

		return value;
	}

	@CachePut(key = "#key")
	public String setValue(String key, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"ValueController.setValue: start, key = {}, value = {}",
					key, value);
		}

		values.put(key, value);
		if (logger.isDebugEnabled()) {
			logger.debug("ValueController.setValue: end, return = {}", value);
		}

		return value;
	}

	@CacheEvict(key = "#key")
	public void deleteValue(String key) {
		values.remove(key);
	}

	@CacheEvict(allEntries = true)
	public void deleteValues() {
		values.clear();
	}

}
