package com.lms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.StudentCourseLogDAO;

@Repository
public class StudentCourseLogDAOImpl implements StudentCourseLogDAO {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public boolean add(int student, int course, String log) {
		String sql = "insert into student_course_log (student, course, logUrl) values (?, ?, ?);";
		try {
			if(jdbc.update(sql, student, course, log) > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int student, int course, String log) {
		String sql = "update student_course_log set logUrl = ? where student = ? and course = ?;";
		try {
			if(jdbc.update(sql, log, student, course) > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getLog(int student, int course) {
		String sql = "select logUrl from student_course_log where student = ? and course = ?;";
		try {
			return jdbc.queryForObject(sql, String.class ,student, course);
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}

	public List<String> getCourseLog(int course) {
		String sql = "select logUrl from student_course_log where course = ?;";
		try {
			return jdbc.queryForList(sql, String.class, course);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
