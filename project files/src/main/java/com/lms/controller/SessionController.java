package com.lms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.entity.LoginInfo;

@CrossOrigin
@RestController
@RequestMapping("/session")
public class SessionController {
	/**
	 * @param request Http请求
	 * @return 创建的LoginInfor对象
	 */
	@GetMapping
	@ResponseBody
	public Object getSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return new LoginInfo(session.getAttribute("username").toString(), session.getAttribute("role").toString());
	}
}
