package com.cyb.cache.demo.domain;

import java.io.Serializable;

/**
 * 学生
 * 
 * @author 01373660
 *
 */
public class Student implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 姓名
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
		return "Student [id=" + id + ", name=" + name + "]";
	}

}
