package com.lms.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.entity.Course;
import com.lms.entity.Message;
import com.lms.entity.Student;
import com.lms.entity.Teacher;
import com.lms.service.CourseService;
import com.lms.service.StudentService;
import com.lms.service.TeacherService;

@CrossOrigin
@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	private CourseService courseService;
	private StudentService studentService;
	private TeacherService teacherService;
	
	/**
	 * @param request Http请求
	 * @param courseName 课程名
	 * @param startDateString 课程开始日期的字符串
	 * @param endDateString 课程结束时间的字符串
	 * @param joinMode 课程加入模式
	 * @return 创建的Message对象
	 * @throws IOException
	 * @throws ParseException
	 */
	@PutMapping("/add")
	@ResponseBody
	public Object addCourse(HttpServletRequest request, 
			@RequestParam(value = "courseName") String courseName, 
			@RequestParam(value = "startDate") String startDateString,
			@RequestParam(value = "endDate") String endDateString,
			@RequestParam(value = "joinMode") int joinMode) throws IOException, ParseException{		
		HttpSession session = request.getSession();
		String teacher = session.getAttribute("username").toString();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
		Date startDate = dateFormat.parse(startDateString + "-08");
		Date endDate = dateFormat.parse(endDateString + "-22");
		Course course = new Course(courseName, teacher, 0, startDate, endDate, joinMode);
		if(courseService.addCourse(course)) {
			return new Message(true, "创建成功");
		} else {
			return new Message(false, "创建失败，请稍后再试或联系管理员");
		}
	}
	
	/**
	 * @param request Http请求
	 * @param courseId 课程的ID
	 * @return 创建的Message对象
	 */
	@PutMapping("/select")
	@ResponseBody
	public Object selectCourse(HttpServletRequest request,
			@RequestParam(value = "courseId") int courseId) {
		HttpSession session = request.getSession();
		Course course = courseService.getCourse(courseId);
		if(course == null) {
			return new Message(false, "无此课程");
		}
		
		if(course.getJoinMode() == 0) {//仅允许主讲教师导入学生名单的情况
			return new Message(false, "无法加入");
		} else if(course.getJoinMode() == 1) {//仅允许本校学生添加的情况
			Student student = studentService.getStudent(session.getAttribute("username").toString());
			Teacher teacher = teacherService.getTeacher(course.getTeacher());
			if(student.getSchool().equals(teacher.getSchool())) {//判定学生是否为本校学生
				if(courseService.selectCourse(student.getUsername(), courseId)) {
					return new Message(true, "选课成功");
				} else {
					return new Message(false, "选课失败，请稍后再试或联系管理员");
				}
			} else {
				return new Message(false, "无法加入");
			}
		} else if (course.getJoinMode() == 2) {//允许任何人通过课程号添加的情况
			if(courseService.selectCourse(session.getAttribute("username").toString(), courseId)) {
				return new Message(true, "选课成功");
			} else {
				return new Message(false, "选课失败，请稍后再试或联系管理员");
			}
		} else {
			return new Message(false, "无法加入");
		}
	}
	
	/**
	 * @param request Http请求
	 * @param courseName 课程名
	 * @param startDateString 课程开始日期的字符串
	 * @param endDateString 课程结束时间的字符串
	 * @param joinMode 课程加入模式
	 * @param courseId 课程的ID
	 * @return 创建的Message对象
	 * @throws ParseException
	 */
	@PostMapping
	@ResponseBody
	public Object updateCourse(HttpServletRequest request, 
			@RequestParam(value = "courseName") String courseName,
			@RequestParam(value = "startDate") String startDateString,
			@RequestParam(value = "endDate") String endDateString,
			@RequestParam(value = "joinMode") int joinMode,
			@RequestParam(value = "courseId") int courseId) throws ParseException {
		HttpSession session = request.getSession();
		String teacher = session.getAttribute("username").toString();
		Course course = new Course();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");

		course.setCourseName(courseName);
		course.setCourseId(courseId);
		course.setTeacher(teacher);
		course.setStartDate(dateFormat.parse(startDateString + "-08"));
		course.setEndDate(dateFormat.parse(endDateString + "-22"));
		course.setJoinMode(joinMode);
		
		if(courseService.updateCourse(course)) {
			return new Message(true, "更新成功");
		} else {
			return new Message(false, "更新失败，请确认课程信息");
		}
	}
	
	/**
	 * @param courseId 课程ID
	 * @return courseService.getCourse(courseId)的结果
	 */
	@GetMapping("/get")
	@ResponseBody
	public Object getCourse(@RequestParam(value = "id") int courseId) {
		return courseService.getCourse(courseId);
	}
		
	/**
	 * @param request Http请求
	 * @return courseService.getCourseListForStudent(session.getAttribute("username").toString())或courseService.getCourseListForTeacher(session.getAttribute("username").toString())的结果
	 */
	@GetMapping("/list")
	@ResponseBody
	public Object getCourseList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("role").toString() == "student") {
			return courseService.getCourseListForStudent(session.getAttribute("username").toString());
		}
		else if(session.getAttribute("role").toString() == "teacher") {
			return courseService.getCourseListForTeacher(session.getAttribute("username").toString());
		}
		else {
			return null;
		}
	}
}
