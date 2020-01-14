package com.cyb.cache.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.cache.demo.service.ValueService;

/**
 * 值控制器
 * 
 * @author 01373660
 *
 */
@RequestMapping("values")
@RestController
public class ValueController {

	/**
	 * 值服务
	 */
	@Autowired
	private ValueService valueService;

	/**
	 * 获取值
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	@GetMapping("{key}")
	public String getValue(@PathVariable String key) {
		return valueService.getValue(key);
	}

	/**
	 * 设置值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	@PostMapping("{key}")
	public void setValue(@PathVariable String key, @RequestBody String value) {
		valueService.setValue(key, value);
	}

	/**
	 * 删除值
	 * 
	 * @param key
	 */
	@DeleteMapping("{key}")
	public void deleteValue(@PathVariable String key) {
		valueService.deleteValue(key);
	}

	/**
	 * 删除值集合
	 */
	@DeleteMapping
	public void deleteValues() {
		valueService.deleteValues();
	}
}
