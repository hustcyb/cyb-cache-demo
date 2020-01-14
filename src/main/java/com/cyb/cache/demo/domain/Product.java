package com.cyb.cache.demo.domain;

/**
 * 产品
 * 
 * @author 01373660
 *
 */
public class Product {

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

}
