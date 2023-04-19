package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.entity.Message;
import com.lms.service.StudentService;
import com.lms.service.TeacherService;

@CrossOrigin
@RestController
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	private StudentService studentService; 
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 创建的Message对象
	 */
	@PutMapping("/student")
	@ResponseBody
	public Object studentRegister(@RequestParam(value = "username") String username, 
			@RequestParam(value = "password") String password) {
		if(studentService.checkUsernameExist(username)) {
			return new Message(false, "此用户已存在，请登录或以其他用户名注册");
		}
		else if(studentService.register(username, password)) {
			return new Message(true, "注册成功");
		}
		else {
			return new Message(false, "注册失败，请稍后再试或联系管理员");
		}
	}
	
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 创建的Message对象
	 */
	@PutMapping("/teacher")
	@ResponseBody
	public Object teacherRegister(@RequestParam(value="username") String username, 
			@RequestParam(value="password") String password) {
		if(teacherService.checkUsernameExist(username)) {
			return new Message(false, "此用户已存在，请登录或以其他用户名注册");
		}
		else if(teacherService.register(username, password)) {
			return new Message(true, "注册成功");
		}
		else {
			return new Message(false, "注册失败，请稍后再试或联系管理员");
		}
	}
}
