package com.lms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lms.entity.Lesson;
import com.lms.impl.CourseDAOImpl;
import com.lms.impl.LessonDAOImpl;

@Service
public class LessonService {
	
	@Autowired
	private LessonDAOImpl lessonDAOImpl;
	@Autowired
	private CourseDAOImpl courseDAOImpl;
	
	/**
	 * @param course 课程ID
	 * @return lessonDAOImpl.add(course)的结果
	 */
	public boolean addLesson(int course) {
		return lessonDAOImpl.add(course);
	}
	
	/**
	 * @param lessonId 课程章节ID
	 * @return lessonDAOImpl.delete(lessonId)的结果
	 */
	public boolean deteleLesson(int lessonId) {
		return lessonDAOImpl.delete(lessonId);
	}
	
	/**
	 * @param lessonId 课程章节ID
	 * @return lessonDAOImpl.getLesson(lessonId)的结果
	 */
	public Lesson getLesson(int lessonId) {
		return lessonDAOImpl.getLesson(lessonId);
	}
	
	/**
	 * @param lessonId 课程章节ID
	 * @param lesson 课程章节对象
	 * @param file 视频文件
	 * @return 上传成功返回True，否则返回false
	 */
	public boolean updateLesson(int lessonId, Lesson lesson, MultipartFile file) {
		//判断更新数据库内容是否成功
		if(lessonDAOImpl.update(lessonId, lesson)) {	
			//判断上传的视频文件是否存在
			if(file == null || file.isEmpty()) {
				return true; 
			}
			String filePath = new File("src/main/webapp/video/" + lessonId).getAbsolutePath();
			File fileUpload = new File(filePath);
			if(fileUpload.exists()) {
				File[] files = fileUpload.listFiles();
				for (File oldfile : files) {
					if(oldfile.isFile()) {
						oldfile.delete();
					}
				}
			}
			else {
				fileUpload.mkdir();
			}
			
			fileUpload = new File(filePath, file.getOriginalFilename());
			
			try {
				file.transferTo(fileUpload);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param course 课程ID
	 * @return 返回获取到的课程信息和课程章节信息列表，列表中第一个对象是课程，之后的对象是课程章节
	 */
	public List<Object> getCourAndLessonList(int course) {
		List<Object> list = new ArrayList<Object>();
		list.add(courseDAOImpl.getCourse(course));
		List<Lesson> lessonList = lessonDAOImpl.getLessonByCourse(course);
		for (Lesson lesson : lessonList) {
			list.add(lesson);
		}
		return list;
	}
}
