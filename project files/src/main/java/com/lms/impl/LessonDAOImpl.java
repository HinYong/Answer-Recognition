package com.lms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.LessonDAO;
import com.lms.entity.Lesson;

@Repository
public class LessonDAOImpl implements LessonDAO {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public boolean add(int course) {
		String sql = "insert into lesson (lessonName, course) values (\"未命名\", ?);";
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
	public boolean delete(int lessonId) {
		String sql = "delete from lesson where lessonId = ?;";
		try {
			if(jdbc.update(sql, lessonId) > 0) {
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
	public boolean update(int lessonId, Lesson lesson) {
		String sql = "update lesson set lessonName = ?, introduction = ?, video = ? where lessonId = ?;";
		try {
			if(jdbc.update(sql, lesson.getLessonName(), lesson.getIntroduction(), lesson.getVideo(), lessonId) > 0) {
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
	public Lesson getLesson(int lessonId) {
		String sql = "select * from lesson where lessonId = ?;";
		try {
			Map<String, Object> map = jdbc.queryForMap(sql, lessonId);
			return mapToLesson(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Lesson> getLessonByCourse(int course) {
		String sql = "select * from lesson where course = ?;";
		try {
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, course);
			List<Lesson> lessonList = new ArrayList<Lesson>();
			for(Map<String, Object> map : mapList) {
				lessonList.add(mapToLesson(map));
			}
			return lessonList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Lesson mapToLesson(Map<String, Object> map) {
		Lesson lesson = new Lesson();
		
		if(map.get("lessonName") != null) {
			lesson.setLessonName(map.get("lessonName").toString());
		}
		if(map.get("lessonId") != null) {
			lesson.setLessonId(Integer.parseInt(map.get("lessonId").toString()));
		}
		if(map.get("course") != null) {
			lesson.setCourse(Integer.parseInt(map.get("course").toString()));
		}
		if(map.get("introduction") != null) {
			lesson.setIntroduction(map.get("introduction").toString());
		}
		if(map.get("video") != null) {
			lesson.setVideo(map.get("video").toString());
		}
		
		return lesson;
	}
}
