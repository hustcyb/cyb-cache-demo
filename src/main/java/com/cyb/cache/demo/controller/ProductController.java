package com.cyb.cache.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.cache.demo.domain.Product;
import com.cyb.cache.demo.service.ProductService;

@RequestMapping("products")
@RestController
public class ProductController {

	/**
	 * 产品服务
	 */
	@Autowired
	private ProductService productService;

	/**
	 * 根据编号获取产品
	 * 
	 * @param id
	 *            编号
	 * @return 产品
	 */
	@GetMapping("{id}")
	public Product getById(@PathVariable Long id) {
		return productService.getById(id);
	}

	/**
	 * 保存产品
	 * 
	 * @param product
	 *            产品
	 */
	@PostMapping
	public void saveProduct(@RequestBody Product product) {
		productService.saveProduct(product);
	}

	/**
	 * 根据编号删除产品
	 * 
	 * @param id
	 *            编号
	 */
	@DeleteMapping("{id}")
	public void deleteById(@PathVariable Long id) {
		productService.deleteById(id);
	}
}
