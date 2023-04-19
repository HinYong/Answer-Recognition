package com.lms.dao;

import java.util.List;

import com.lms.entity.Assignment;

public interface AssignmentDAO {
	/**
	 * @param course 作业所属的课程ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(int course);
	
	/**
	 * @param assignmentId 作业ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int assignmentId);
	
	/**
	 * @param assignmentId 作业ID
	 * @param assignment 作业对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(int assignmentId, Assignment assignment);
	
	/**
	 * @param assignmentId 作业ID
	 * @return 获取的作业对象
	 */
	Assignment getAssignment(int assignmentId);
	
	/**
	 * @param course 课程ID
	 * @return 获取的作业列表
	 */
	List<Assignment> getAssignmentByCourse(int course);
}
