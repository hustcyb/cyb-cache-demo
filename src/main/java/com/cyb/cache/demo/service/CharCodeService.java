package com.cyb.cache.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 字符编码服务
 * 
 * @author 01373660
 *
 */
@CacheConfig(cacheNames = "CHCODE")
@Service
public class CharCodeService {

	/**
	 * 日志接口
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CharCodeService.class);

	/**
	 * 字符编码服务，修正调用自身方法时注解不生效的问题
	 */
	@Autowired
	private CharCodeService self;

	/**
	 * 获取字符编码
	 * 
	 * @param ch
	 *            字符
	 * @return 字符编码
	 */
	@Cacheable(key = "#ch")
	public int getCode(char ch) {
		if (logger.isDebugEnabled()) {
			logger.debug("CharCodeService.getCharCode: start, ch = {}", ch);
		}

		int charCode = ch;
		if (logger.isDebugEnabled()) {
			logger.debug("CharCodeService.getCharCode: end, return = {}",
					charCode);
		}

		return charCode;
	}

	/**
	 * 获取字符十六进制格式字符串
	 * 
	 * @param ch
	 *            字符
	 * @return 字符编码十六进制形式
	 */
	public String getHexString(char ch) {
		int code = self.getCode(ch);
		return Integer.toHexString(code);
	}

	/**
	 * 获取字符Unicode编码字符串
	 * 
	 * @param ch
	 *            字符
	 * @return Unicode编码字符串
	 */
	public String getUnicodeString(char ch) {
		int code = getCode(ch);
		return "\\u" + Integer.toHexString(code);
	}
}
