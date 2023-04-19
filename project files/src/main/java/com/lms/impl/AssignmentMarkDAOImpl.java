package com.lms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.AssignmentMarkDAO;
import com.lms.entity.AssignmentMark;

@Repository
public class AssignmentMarkDAOImpl implements AssignmentMarkDAO {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public boolean add(AssignmentMark assignmentMark) {
		String sql = "insert into assignment_mark (assignment, student, mark) values (?,?,?);";
		try {
			if(jdbc.update(sql, assignmentMark.getAssignment(), assignmentMark.getStudent(), assignmentMark.getMark()) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int assignment, String student) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(AssignmentMark assignmentMark) {
		String sql = "update assignment_mark set mark = ? where assignment = ?, student = ?;";
		try {
			if(jdbc.update(sql, assignmentMark.getMark(), assignmentMark.getAssignment(), assignmentMark.getStudent()) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public AssignmentMark getAssignmentMark(int assignment, int student) {
		String sql = "select * from assignment_mark where assignment = ? and student = ?;";
		try {
			Map<String, Object> map = jdbc.queryForMap(sql, assignment, student);
			return mapToAssignmentMark(map);
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public double getStudentMark(int course, int student) {
		String sql = "select * from assignment join assignment_mark on assignment.assignmentId = assignment_mark.assignment where course = ? and student = ?;";
		try {
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, course, student);
			double mark = 0;
			float weight;
			int assignmentMark;
			for (Map<String, Object> map : mapList) {
				weight = Float.parseFloat(map.get("weight").toString());
				assignmentMark = Integer.parseInt(map.get("mark").toString());
				mark += weight * assignmentMark;
			}
			return mark;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public AssignmentMark mapToAssignmentMark(Map<String, Object> map) {
		AssignmentMark assignmentMark = new AssignmentMark();
		
		if(map.get("assignment") != null) {
			assignmentMark.setAssignment(Integer.parseInt(map.get("assignment").toString()));
		}
		if(map.get("student") != null) {
			assignmentMark.setStudent(Integer.parseInt(map.get("student").toString()));
		}
		if(map.get("mark") != null) {
			assignmentMark.setMark(Integer.parseInt(map.get("mark").toString()));
		} else {
			return null;
		}
		
		return assignmentMark;
	}
}
