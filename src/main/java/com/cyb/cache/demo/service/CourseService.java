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

import com.cyb.cache.demo.domain.Course;

/**
 * 课程服务
 * 
 * @author 01373660
 *
 */
@CacheConfig(cacheNames = "CRS", cacheManager = "jcacheCacheManager")
@Service
public class CourseService {

	/**
	 * 日志接口
	 */
	private static Logger logger = LoggerFactory
			.getLogger(CourseService.class);

	/**
	 * 课程字典
	 */
	private static final Map<Long, Course> courses = new HashMap<>();

	/**
	 * 根据编号获取课程
	 * 
	 * @param id
	 *            编号
	 * @return 课程
	 */
	@Cacheable(key = "#id")
	public Course getById(Long id) {
		if (logger.isDebugEnabled()) {
			logger.debug("CourseService.getById: start, id = {}", id);
		}

		Course course = courses.get(id);
		if (logger.isDebugEnabled()) {
			logger.debug("CourseService.getById: end, return = {}", course);
		}

		return course;
	}

	/**
	 * 保存课程
	 * 
	 * @param course
	 *            课程
	 * @return 课程
	 */
	@CachePut(key = "#course.id")
	public Course saveCourse(Course course) {
		if (logger.isDebugEnabled()) {
			logger.debug("CourseService.saveCourse: start, course = {}",
					course);
		}

		Long id = course.getId();
		courses.put(id, course);
		if (logger.isDebugEnabled()) {
			logger.debug("CourseService.saveCourse: end, return = {}",
					course);
		}

		return course;
	}

	/**
	 * 根据编号删除课程
	 * 
	 * @param id
	 *            编号
	 */
	@CacheEvict(key = "#id")
	public void deleteById(Long id) {
		if (logger.isDebugEnabled()) {
			logger.debug("CourseService.deleteById: start, id = {}", id);
		}

		courses.remove(id);
		if (logger.isDebugEnabled()) {
			logger.debug("CourseService.deleteById");
		}
	}

}
