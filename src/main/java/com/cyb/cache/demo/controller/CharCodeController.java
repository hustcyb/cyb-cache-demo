package com.cyb.cache.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.cache.demo.service.CharCodeService;

/**
 * 字符编码控制器
 * 
 * @author 01373660
 *
 */
@RequestMapping("char-codes")
@RestController
public class CharCodeController {

	/**
	 * 字符编码服务
	 */
	@Autowired
	private CharCodeService charCodeService;

	/**
	 * 获取字符编码
	 * 
	 * @param ch
	 *            字符
	 * @return 字符编码
	 */
	@GetMapping("{ch}")
	public int getCode(@PathVariable char ch) {
		return charCodeService.getCode(ch);
	}

	/**
	 * 获取字符十六进制格式字符串
	 * 
	 * @param ch
	 *            字符
	 * @return 十六进制格式字符串
	 */
	@GetMapping(path = "{ch}", params = "format=hex")
	public String getHexCode(@PathVariable char ch) {
		return charCodeService.getHexString(ch);
	}

	/**
	 * 获取字符Unicode编码字符串
	 * 
	 * @param ch
	 *            字符
	 * @return Unicode编码字符串
	 */
	@GetMapping(path = "{ch}", params = "format=unicode")
	public String getUnicodeString(@PathVariable char ch) {
		return charCodeService.getUnicodeString(ch);
	}
}
