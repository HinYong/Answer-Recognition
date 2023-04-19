package com.lms.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.lms.service.StudentCourseLogService;

@CrossOrigin
@RestController
@RequestMapping("/log")
public class LogController {
	@Autowired
	private StudentCourseLogService service;
	
	/**
	 * @param request Http请求
	 * @param course Log所属的课程ID
	 * @param log Log内容
	 */
	@PostMapping
	public void updateLog(HttpServletRequest request, 
			@RequestParam(value = "course") int course, 
			@RequestParam(value = "log") String log) {
		System.out.println(log);
		HttpSession session = request.getSession();
		String student = session.getAttribute("username").toString();
		if(!service.receiveLog(student, course, log)) {
			System.err.println("Error when receive new log data.");
		}
	}
	
	/**
	 * @param course Log所属的课程ID
	 * @param username Log所属的账号用户名
	 * @return Object列表形式的log内容
	 */
	@GetMapping
	@ResponseBody
	public Object getLog(@RequestParam(value = "id") int course, 
			@RequestParam(value = "u") String username) {
		if(username.equals("all")) {
			List<Object> logs = new ArrayList<Object>();
			List<String> logList = service.getCourseLog(course);
			for (String logFile : logList) {
				List<Object> log = new ArrayList<Object>();
				if(logFile != null) {
					List<String> logData = service.readLog(logFile);
					for (String line : logData) {
						log.add(line);
					}
					logs.add(log);
				}
			}
			return logs;
		} else {
			List<Object> log = new ArrayList<Object>();
			String logFile = service.getLog(username, course);
			log.add(logFile);
			if(logFile != null) {
				List<String> logData = service.readLog(logFile);
				for (String line : logData) {
					log.add(line);
				}
			}
			return log;
		}
	}
}
