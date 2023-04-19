package com.lms.dao;

import java.util.List;

import com.lms.entity.Course;

public interface CourseDAO {
	/**
	 * @param courseName 课程名
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(String courseName);
	
	/**
	 * @param course 课程对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(Course course);
	
	/**
	 * @param courseId 课程ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int courseId);
	
	/**
	 * @param courseId 课程ID
	 * @param course 课程对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(int courseId, Course course);

	/**
	 * @param courseId 课程ID
	 * @return 获取到的课程对象
	 */
	Course getCourse(int courseId);
	
	/**
	 * @param teacher 教师ID
	 * @return 获取到的课程列表
	 */
	List<Course> getCoursesByTeahcer(int teacher);
}
