package com.cyb.cache.demo.domain;

import java.io.Serializable;

/**
 * 课程信息
 * 
 * @author 01373660
 *
 */
public class Course implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

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
		return "Course [id=" + id + ", name=" + name + "]";
	}

}
