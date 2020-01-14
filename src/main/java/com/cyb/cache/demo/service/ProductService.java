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

import com.cyb.cache.demo.domain.Product;

/**
 * 产品服务
 * 
 * @author 01373660
 *
 */
@CacheConfig(cacheNames = "PROD")
@Service
public class ProductService {

	/**
	 * 日志接口
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ProductService.class);

	/**
	 * 产品字典
	 */
	private static final Map<Long, Product> products = new HashMap<>();

	/**
	 * 根据编号获取产品
	 * 
	 * @param id
	 *            编号
	 * @return 产品
	 */
	@Cacheable(key = "#id")
	public Product getById(Long id) {
		if (logger.isDebugEnabled()) {
			logger.debug("ProductService.getById: start, id = {}", id);
		}

		Product Product = products.get(id);
		if (logger.isDebugEnabled()) {
			logger.debug("ProductService.getById: end, return = {}", Product);
		}

		return Product;
	}

	/**
	 * 保存产品
	 * 
	 * @param product
	 *            产品
	 * @return 产品
	 */
	@CachePut(key = "#product.id")
	public Product saveProduct(Product product) {
		if (logger.isDebugEnabled()) {
			logger.debug("ProductService.saveProduct: start, product = {}",
					product);
		}

		Long id = product.getId();
		products.put(id, product);
		if (logger.isDebugEnabled()) {
			logger.debug("ProductService.saveProduct: end, return = {}",
					product);
		}

		return product;
	}

	/**
	 * 根据编号删除产品
	 * 
	 * @param id
	 *            编号
	 */
	@CacheEvict(key = "#id")
	public void deleteById(Long id) {
		if (logger.isDebugEnabled()) {
			logger.debug("ProductService.deleteById: start, id = {}", id);
		}

		products.remove(id);
		if (logger.isDebugEnabled()) {
			logger.debug("ProductService.deleteById");
		}
	}

}
