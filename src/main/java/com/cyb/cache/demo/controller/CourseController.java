package com.cyb.cache.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.cache.demo.domain.Course;
import com.cyb.cache.demo.service.CourseService;

/**
 * 课程控制器
 * 
 * @author 01373660
 *
 */
@RequestMapping("courses")
@RestController
public class CourseController {

	/**
	 * 课程服务
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 根据编号获取课程
	 * 
	 * @param id
	 *            编号
	 * @return 课程
	 */
	@GetMapping("{id}")
	public Course getById(@PathVariable Long id) {
		return courseService.getById(id);
	}

	/**
	 * 保存课程
	 * 
	 * @param course
	 *            课程
	 */
	@PostMapping
	public void saveCourse(@RequestBody Course course) {
		courseService.saveCourse(course);
	}

	/**
	 * 根据编号删除课程
	 * 
	 * @param id
	 *            编号
	 */
	@DeleteMapping("{id}")
	public void deleteById(@PathVariable Long id) {
		courseService.deleteById(id);
	}

}
