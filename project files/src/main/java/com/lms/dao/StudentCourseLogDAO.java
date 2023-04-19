package com.lms.dao;

public interface StudentCourseLogDAO {
	
	/**
	 * @param student
	 * @param course
	 * @param log
	 * @return
	 */
	boolean add(int student, int course, String log);
	
	/**
	 * @param course 课程ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int course);
	
	/**
	 * @param student 学生ID
	 * @param course 课程ID
	 * @param log log内容
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(int student, int course, String log);
	
	/**
	 * @param student 学生ID
	 * @param course 课程ID
	 * @return 获取到的log内容
	 */
	String getLog(int student, int course);
}
