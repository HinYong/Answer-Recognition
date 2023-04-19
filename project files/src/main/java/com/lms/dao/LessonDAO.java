package com.lms.dao;

import java.util.List;

import com.lms.entity.Lesson;

public interface LessonDAO {
	/**
	 * @param course 课程ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(int course);
	
	/**
	 * @param lessonId 课程章节ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int lessonId);
	
	/**
	 * @param lessonId 课程章节ID
	 * @param lesson 课程章节对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(int lessonId, Lesson lesson);
	
	/**
	 * @param lessonId 课程章节ID
	 * @return 获取到的课程章节对象
	 */
	Lesson getLesson(int lessonId);
	
	/**
	 * @param course 课程ID
	 * @return 获取到的课程章节列表
	 */
	List<Lesson> getLessonByCourse(int course);
}
