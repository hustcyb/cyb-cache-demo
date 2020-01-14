package com.cyb.cache.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.cache.demo.domain.Student;
import com.cyb.cache.demo.service.StudentService;

/**
 * 学生控制器
 * 
 * @author 01373660
 *
 */
@RequestMapping("students")
@RestController
public class StudentController {

	/**
	 * 学生服务
	 */
	@Autowired
	private StudentService studentService;

	/**
	 * 根据编号获取学生
	 * 
	 * @param id
	 *            编号
	 * @return 学生
	 */
	@GetMapping("{id}")
	public Student getById(@PathVariable Long id) {
		return studentService.getById(id);
	}

	/**
	 * 保存学生
	 * 
	 * @param student
	 *            学生
	 */
	@PostMapping
	public void saveStudent(@RequestBody Student student) {
		studentService.saveStudent(student);
	}

	/**
	 * 根据编号删除学生
	 * 
	 * @param id
	 *            编号
	 */
	@DeleteMapping("{id}")
	public void deleteById(@PathVariable Long id) {
		studentService.deleteById(id);
	}
}
