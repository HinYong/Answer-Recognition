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

import com.lms.dao.CourseDAO;
import com.lms.entity.Course;
import com.lms.entity.Teacher;

@Repository
public class CourseDAOImpl implements CourseDAO {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private TeacherDAOImpl teacherDAOImpl;
	
	@Override
	public boolean add(String courseName) {
		String sql = "insert into course (courseName) values (?);";
		try {
			if(jdbc.update(sql, courseName) > 0) {
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
	public boolean add(Course course) {
		String sql = "insert into course (courseName, teacher, startDate, endDate, joinMode) values (?, ?, ?, ?, ?);";
		try {
			Teacher tempTeacher = teacherDAOImpl.getTeacher(course.getTeacher());
			if(jdbc.update(sql, course.getCourseName(), tempTeacher.getTeacherId(), course.getStartDate(), course.getEndDate(), course.getJoinMode()) > 0) {
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
	public boolean delete(int courseId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int courseId, Course course) {
		String sql = "update course set courseName = ?, teacher = ?, startDate = ?, endDate = ?, joinMode = ? where courseId = ?;";
		try {
			Teacher tempTeacher = teacherDAOImpl.getTeacher(course.getTeacher());
			if(jdbc.update(sql, course.getCourseName(), tempTeacher.getTeacherId(), course.getStartDate(), course.getEndDate(), course.getJoinMode(), courseId) > 0) {
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
	public Course getCourse(int courseId) {
		String sql = "select * from course where courseId = ?;";
		try {
			Map<String, Object> map = jdbc.queryForMap(sql,courseId);
			return mapToCourse(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Course> getCoursesByTeahcer(int teacher) {
		String sql = "select * from course where teacher = ?;";
		try {
			Teacher tempTeacher = teacherDAOImpl.getTeacher(teacher);
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, tempTeacher.getTeacherId());
			List<Course> courseList = new ArrayList<Course>();
			for (Map<String, Object> map : mapList) {
				courseList.add(getCourse(Integer.parseInt(map.get("courseId").toString())));
			}
			return courseList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public Course mapToCourse(Map<String, Object> map) throws ParseException {
		Course course = new Course();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
		
		if(map.get("courseName") != null) {
			course.setCourseName(map.get("courseName").toString());
		}
		if(map.get("teacher") != null) {
			course.setTeacher(map.get("teacher").toString());
		}
		if(map.get("courseId") != null) {
			course.setCourseId(Integer.parseInt(map.get("courseId").toString()));
		}
		if(map.get("startDate") != null) {
			course.setStartDate(dateFormat.parse(map.get("startDate").toString() + "-08"));
		}
		if(map.get("endDate") != null) {
			course.setEndDate(dateFormat.parse(map.get("endDate").toString() + "-22"));
		}
		if(map.get("joinMode") != null) {
			course.setJoinMode(Integer.parseInt(map.get("joinMode").toString()));
		}
		
		return course;
	}
}
