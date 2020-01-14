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

import com.cyb.cache.demo.domain.Student;

/**
 * 学生服务
 * 
 * @author 01373660
 *
 */
@CacheConfig(cacheNames = "STU")
@Service
public class StudentService {

	/**
	 * 日志接口
	 */
	private static Logger logger = LoggerFactory
			.getLogger(StudentService.class);

	/**
	 * 学生字典
	 */
	private static final Map<Long, Student> students = new HashMap<>();

	/**
	 * 根据编号获取学生
	 * 
	 * @param id
	 *            编号
	 * @return 学生
	 */
	@Cacheable(key = "#id")
	public Student getById(Long id) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.getById: start, id = {}", id);
		}

		Student student = students.get(id);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.getById: end, return = {}", student);
		}

		return student;
	}

	/**
	 * 保存学生
	 * 
	 * @param student
	 *            学生
	 * @return 学生
	 */
	@CachePut(key = "#student.id")
	public Student saveStudent(Student student) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.saveStudent: start, student = {}",
					student);
		}

		Long id = student.getId();
		students.put(id, student);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.saveStudent: end, return = {}",
					student);
		}

		return student;
	}

	/**
	 * 根据编号删除学生
	 * 
	 * @param id
	 *            编号
	 */
	@CacheEvict(key = "#id")
	public void deleteById(Long id) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.deleteById: start, id = {}", id);
		}

		students.remove(id);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.deleteById");
		}
	}
}
