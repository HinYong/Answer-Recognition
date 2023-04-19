package com.lms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.service.AssignmentMarkService;

@CrossOrigin
@RestController
@RequestMapping("/assignmentMark")
public class AssignmentMarkController {
	@Autowired
	private AssignmentMarkService assignmentMarkService;
	
	/**
	 * @param request Http请求
	 * @param data 答案的数据内容
	 * @return assignmentMarkService.calculateMark(data, student)的结果
	 */
	@PutMapping
	@ResponseBody
	public boolean receviceAnswers(HttpServletRequest request, @RequestParam(value = "data") String data) {
		System.out.println(data);
		HttpSession session = request.getSession();
		String student = session.getAttribute("username").toString();
		System.out.println(student);
		return assignmentMarkService.calculateMark(data, student);
	}
	
	/**
	 * @param request Http请求
	 * @param assignment 作业的ID
	 * @return assignmentMarkService.getMark(assignment, student)的结果
	 */
	@GetMapping
	@ResponseBody
	public Object getMark(HttpServletRequest request, @RequestParam(value = "assignment") int assignment) {
		HttpSession session = request.getSession();
		String student = session.getAttribute("username").toString();
		return assignmentMarkService.getMark(assignment, student);
	}
}
