package com.lms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.entity.Message;
import com.lms.service.StudentService;
import com.lms.service.TeacherService;

@CrossOrigin
@RestController
@RequestMapping("/information")
public class InformationController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * @param request Http请求
	 * @return studentService.getStudent(username)或teacherService.getTeacher(username)的结果
	 */
	@GetMapping
	@ResponseBody
	public Object getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		if(session.getAttribute("role").toString().equals("student")) {
			return studentService.getStudent(username);
		} else if (session.getAttribute("role").toString().equals("teacher")) {
			return teacherService.getTeacher(username);
		} else {
			return null;
		}
	}
	
	/**
	 * @param request Http请求
	 * @param realname 真实姓名
	 * @param phone 电话号码
	 * @param email 电子邮箱
	 * @return 创建的Message对象
	 */
	@PostMapping("/information")
	@ResponseBody
	public Object updateInformation(HttpServletRequest request, 
			@RequestParam(value = "realname") String realname, 
			@RequestParam(value = "phone") String phone,
			@RequestParam(value = "email") String email) {
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		if(session.getAttribute("role").toString().equals("student")) {
			if(studentService.updateInformation(username, realname, phone, email)) {
				return new Message(true, "保存成功");
			} else {
				return new Message(false, "保存失败，请检查登录状况");
			}
		} else if (session.getAttribute("role").toString().equals("teacher")) {
			if(teacherService.updateInformation(username, realname, phone, email)) {
				return new Message(true, "保存成功");
			} else {
				return new Message(false, "保存失败，请检查登录状况");
			}
		} else {
			return new Message(false, "保存失败，请检查登录状况");
		}
	}
	

	/**
	 * @param request Http请求
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @return 创建的Message对象
	 */
	@PostMapping("/password")
	@ResponseBody
	public Object updatePassword(HttpServletRequest request, 
			@RequestParam(value = "oldPassword") String oldPassword, 
			@RequestParam(value = "newPassword") String newPassword) {
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		if(session.getAttribute("role").toString().equals("student")) {
			if(studentService.updatePassword(username, oldPassword, newPassword)) {
				return new Message(true, "保存成功");
			} else {
				return new Message(false, "保存失败，请检查密码");
			}
		} else if (session.getAttribute("role").toString().equals("teacher")) {
			if(teacherService.updatePassword(username, oldPassword, newPassword)) {
				return new Message(true, "保存成功");
			} else {
				return new Message(false, "保存失败，请检查密码");
			}
		} else {
			return new Message(false, "保存失败，请检查登录状况");
		}
	}
	
	/**
	 * @param request Http请求
	 * @param school 学校名
	 * @param studentNumber 学号
	 * @param department 院系
	 * @param schoolClass 班级
	 * @return 创建的Message对象
	 */
	@PostMapping("/schoolInformation")
	@ResponseBody
	public Object updateSchoolInformation(HttpServletRequest request, 
			@RequestParam(value = "school") String school,
			@RequestParam(value = "studentNumber") String studentNumber,
			@RequestParam(value = "department") String department,
			@RequestParam(value = "schoolClass") String schoolClass) {
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		if(session.getAttribute("role").toString().equals("student")) {
			if(studentService.updateSchoolInformation(username, school, studentNumber, department, schoolClass)) {
				return new Message(true, "保存成功");
			} else {
				return new Message(false, "保存失败，请检查密码");
			}
		} else {
			return new Message(false, "保存失败，请检查登录状况");
		}
	}
	
	/**
	 * @param request Http请求
	 * @param school 学校
	 * @param teacherNumber 教工号
	 * @param department 院系
	 * @return 创建的Message对象
	 */
	@PostMapping("/teacherInformation")
	@ResponseBody
	public Object updateSchoolInformation(HttpServletRequest request, 
			@RequestParam(value = "school") String school,
			@RequestParam(value = "teacherNumber") String teacherNumber,
			@RequestParam(value = "department") String department) {
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		if(session.getAttribute("role").toString().equals("teacher")) {
			if(teacherService.updateSchoolInformation(username, school, teacherNumber, department)) {
				return new Message(true, "保存成功");
			} else {
				return new Message(false, "保存失败，请检查密码");
			}
		} else {
			return new Message(false, "保存失败，请检查登录状况");
		}
	}
}
