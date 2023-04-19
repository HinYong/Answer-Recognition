package com.lms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lms.entity.Lesson;
import com.lms.entity.Message;
import com.lms.service.LessonService;

@CrossOrigin
@RestController
@RequestMapping("/lesson")
public class LessonController {
	@Autowired
	private LessonService lessonService;
	
	/**
	 * @param request Http请求
	 * @param course 课程章节所属的课程ID
	 * @return lessonService.addLesson(course)的结果
	 */
	@PutMapping
	@ResponseBody
	public Object addLesson(HttpServletRequest request, @RequestParam(value = "id") int course) {
		return lessonService.addLesson(course);
	}
	
	/**
	 * @param request Http请求
	 * @param lessonId 课程章节ID
	 * @return lessonService.deteleLesson(lessonId)的结果
	 */
	@DeleteMapping
	@ResponseBody
	public Object deleteLesson(HttpServletRequest request, @RequestParam(value = "lessonId") int lessonId) {
		return lessonService.deteleLesson(lessonId);
	}
	
	/**
	 * @param lessonName 课程章节名
	 * @param lessonId 课程章节ID
	 * @param introduction 课程章节介绍
	 * @param file 课程章节视频文件
	 * @return 创建的Message对象
	 */
	@PostMapping
	@ResponseBody
	public Object updateLesson(@RequestParam("lessonName") String lessonName,
			@RequestParam("lessonId") int lessonId,
			@RequestParam("introduction") String introduction,
			@RequestParam("video") MultipartFile file) {
		Lesson lesson = lessonService.getLesson(lessonId);
		lesson.setLessonName(lessonName);
		lesson.setIntroduction(introduction);
		lesson.setVideo("../video/" + lessonId + "/" + file.getOriginalFilename());
		if(lessonService.updateLesson(lessonId, lesson, file)) {
			return new Message(true, "完成");
		} else {
			return new Message(true, "更新失败");
		}

	}
	
	/**
	 * @param lessonId 课程章节ID
	 * @return lessonService.getLesson(lessonId)的结果
	 */
	@GetMapping("/get")
	@ResponseBody
	public Object getLesson(@RequestParam(value = "id") int lessonId) {
		return lessonService.getLesson(lessonId);
	}
	
	/**
	 * @param request Http请求
	 * @param course 课程章节所属的课程ID
	 * @return lessonService.getCourAndLessonList(course)的结果
	 */
	@GetMapping("/list")
	@ResponseBody
	public Object getLessonList(HttpServletRequest request, @RequestParam(value = "id") int course) {
		return lessonService.getCourAndLessonList(course);
	}
}
