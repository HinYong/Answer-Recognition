package com.lms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.StudentCourseDAO;
import com.lms.entity.Course;
import com.lms.entity.Student;

@Repository
public class StudentCourseDAOImpl implements StudentCourseDAO {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private StudentDAOImpl studentDAOImpl;
	@Autowired
	private CourseDAOImpl courseDAOImpl;
	
	@Override
	public boolean add(int student, int course) {
		String sql = "insert into student_course (student, course) values (?, ?);";
		try {
			if(jdbc.update(sql, student, course) > 0) {
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
	public boolean delete(int student, int course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Student> getStudentsByCourse(int course) {
		String sql = "select * from student_course where course = ?;";
		try {
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, course);
			List<Student> studentList = new ArrayList<Student>();
			for (Map<String, Object> map : mapList) {
				studentList.add(studentDAOImpl.getStudent(Integer.parseInt(map.get("student").toString())));
			}
			return studentList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Course> getCoursesByStudent(int student) {
		String sql = "select * from student_course where student = ?;";
		try {
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, student);
			List<Course> courseList = new ArrayList<Course>();
			for (Map<String, Object> map : mapList) {
				courseList.add(courseDAOImpl.getCourse(Integer.parseInt(map.get("course").toString())));
			}
			return courseList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
