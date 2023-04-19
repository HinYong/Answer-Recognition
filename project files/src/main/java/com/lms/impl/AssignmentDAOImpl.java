package com.lms.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.AssignmentDAO;
import com.lms.entity.Assignment;

@Repository
public class AssignmentDAOImpl implements AssignmentDAO {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public boolean add(int course) {
		String sql = "insert into assignment (course) values (?);";
		try {
			if(jdbc.update(sql, course) > 0) {
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
	public boolean delete(int assignmentId) {
		String sql = "delete from assignment where assignmentId = ?;";
		try {
			if(jdbc.update(sql, assignmentId) > 0) {
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
	public boolean update(int assignmentId, Assignment assignment) {
		String sql = "update assignment set assignmentName = ?, weight = ?, startTime = ?, endTime = ? where assignmentId = ?;";
		try {
			if (jdbc.update(sql, assignment.getAssignmentName(), assignment.getWeight(), assignment.getStartTime(), assignment.getEndTime(), assignmentId) > 0) {
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
	public Assignment getAssignment(int assignmentId) {
		String sql = "select * from assignment where assignmentId = ?;";
		try {
			Map<String, Object> map = jdbc.queryForMap(sql, assignmentId);
			return mapToAssignment(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Assignment> getAssignmentByCourse(int course) {
		String sql = "select * from Assignment where course = ?;";
		try {
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, course);
			List<Assignment> assignmentList = new ArrayList<Assignment>();
			for (Map<String, Object> map : mapList) {
				assignmentList.add(getAssignment(Integer.parseInt(map.get("assignmentId").toString())));
			}
			return assignmentList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Assignment mapToAssignment(Map<String, Object> map) throws ParseException {
		Assignment assignment = new Assignment();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		if (map.get("assignmentId") != null) {
			assignment.setAssignmentId(Integer.parseInt(map.get("assignmentId").toString()));
		}
		if (map.get("course") != null) {
			assignment.setCourse(Integer.parseInt(map.get("course").toString()));
		}
		if (map.get("assignmentName") != null) {
			assignment.setAssignmentName(map.get("assignmentName").toString());
		}
		if (map.get("weight") != null) {
			assignment.setWeight(Float.parseFloat(map.get("weight").toString()));
		}
		if (map.get("startTime") != null) {
			assignment.setStartTime(dateFormat.parse(map.get("startTime").toString()));
		}
		if (map.get("endTime") != null) {
			assignment.setEndTime(dateFormat.parse(map.get("endTime").toString()));
		}
		
		return assignment;
	}
}
