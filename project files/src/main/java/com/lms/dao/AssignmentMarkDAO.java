package com.lms.dao;

import com.lms.entity.AssignmentMark;

public interface AssignmentMarkDAO {
	
	/**
	 * @param assignmentMark 作业成绩对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(AssignmentMark assignmentMark);
	
	/**
	 * @param assignment 作业ID
	 * @param student 学生用户名
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int assignment, String student);
	
	/**
	 * @param assignmentMark 作业成绩对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(AssignmentMark assignmentMark);
	
	/**
	 * @param assignment 作业ID
	 * @param student 学生ID
	 * @return 对应作业学生的成绩
	 */
	AssignmentMark getAssignmentMark(int assignment, int student);
	
	/**
	 * @param course 课程ID
	 * @param student 学生ID
	 * @return 对应课程学生的成绩
	 */
	double getStudentMark(int course, int student);

}
