package com.lms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/index")
public class LoginController {
	@Autowired
	private StudentService studentService; 
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * @param request Http请求
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 创建的Message对象
	 * @throws IOException
	 */
	@PostMapping("/student")
	@ResponseBody
	public Object studentRegister(HttpServletRequest request,
			@RequestParam(value="username") String username, 
			@RequestParam(value="password") String password) throws IOException {
		if(studentService.checkPassword(username, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("role", "student");
			return new Message(true, "登录成功");
		}else{
			return new Message(false, "登录失败，用户名或密码错误");
		}
	}
	
	/**
	 * @param request Http请求
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 创建的Message对象
	 * @throws IOException
	 */
	@PostMapping("/teacher")
	@ResponseBody
	public Object teacherRegister(HttpServletRequest request,
			@RequestParam(value="username") String username, 
			@RequestParam(value="password") String password) throws IOException {
		if(teacherService.checkPassword(username, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("role", "teacher");
			return new Message(true, "登录成功");
		}else{
			return new Message(false, "登录失败，用户名或密码错误");
		}
	}
}
