package com.lms.dao;

import java.util.List;

import com.lms.entity.Course;
import com.lms.entity.Student;

public interface StudentCourseDAO {
	/**
	 * @param student 学生ID
	 * @param course 课程ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(int student, int course);
	
	/**
	 * @param student 学生ID
	 * @param course 课程ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int student, int course);
	
	/**
	 * @param course 课程ID
	 * @return 获取到的学生列表
	 */
	List<Student> getStudentsByCourse(int course);
	
	/**
	 * @param student 学生ID
	 * @return 获取到的课程列表
	 */
	List<Course> getCoursesByStudent(int student);
}
