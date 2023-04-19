package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.Assignment;
import com.lms.impl.AssignmentDAOImpl;

@Service
public class AssignmentService {
	@Autowired
	private AssignmentDAOImpl assignmentDAOImpl;
	
	/**
	 * @param course 课程ID
	 * @return assignmentDAOImpl.add(course)的结果
	 */
	public boolean addAssignment(int course) {
		return assignmentDAOImpl.add(course);
	}
	
	/**
	 * @param assignmentId 作业ID
	 * @return assignmentDAOImpl.delete(assignmentId)的结果
	 */
	public boolean deleteAssignment(int assignmentId) {
		return assignmentDAOImpl.delete(assignmentId);
	}
	
	/**
	 * @param assignmentId 作业ID
	 * @param assignment 作业对象
	 * @return assignmentDAOImpl.update(assignmentId, assignment)的结果
	 */
	public boolean updateAssignment(int assignmentId, Assignment assignment) {
		return assignmentDAOImpl.update(assignmentId, assignment);
	}
	
	/**
	 * @param assignmentId 作业ID
	 * @return assignmentDAOImpl.getAssignment(assignmentId)的结果
	 */
	public Assignment getAssignment(int assignmentId) {
		return assignmentDAOImpl.getAssignment(assignmentId);
	}
	
	/**
	 * @param course 课程ID
	 * @return assignmentDAOImpl.getAssignmentByCourse(course)的结果
	 */
	public List<Assignment> getAssignmentList(int course) {
		return assignmentDAOImpl.getAssignmentByCourse(course);
	}
}
