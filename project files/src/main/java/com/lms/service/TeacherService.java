package com.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.Teacher;
import com.lms.impl.TeacherDAOImpl;

@Service
public class TeacherService {
	@Autowired
	private TeacherDAOImpl teacherDAOImpl;
	
	/**
	 * @param username 账号用户名
	 * @return 存在此用户名返回True，否则返回False
	 */
	public boolean checkUsernameExist(String username) {
		return teacherDAOImpl.getTeacher(username) != null ? true : false;
	}
	
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return teacherDAOImpl.add(username, password)的结果
	 */
	public boolean register(String username, String password) {
		return teacherDAOImpl.add(username, password);
	}
	
	/**
	 * @param username 账号用户名
	 * @return teacherDAOImpl.getTeacher(username)的结果
	 */
	public Teacher getTeacher(String username) {
		return teacherDAOImpl.getTeacher(username);
	}
	
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 用户名和密码正确匹配返回True，否则返回False
	 */
	public boolean checkPassword(String username, String password) {
		Teacher tempTeacher = teacherDAOImpl.getTeacher(username);
		if(tempTeacher != null) {
			if(password.equals(tempTeacher.getPassword())) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param username 账号用户名
	 * @param realname 真实姓名
	 * @param phone 电话号码
	 * @param email 电子邮件
	 * @return 存在对应账号返回teacherDAOImpl.update(username, tempTeacher)的结果，否则返回False
	 */
	public boolean updateInformation(String username, String realname, String phone, String email) {
		Teacher tempTeacher = teacherDAOImpl.getTeacher(username);
		if(tempTeacher != null) {
			tempTeacher.setRealname(realname);
			tempTeacher.setPhone(phone);
			tempTeacher.setEmail(email);
			return teacherDAOImpl.update(username, tempTeacher);
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param username 账号用户名
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @return 账号密码正确匹配则返回teacherDAOImpl.update(username, tempTeacher)的结果，否则返回Fasle
	 */
	public boolean updatePassword(String username, String oldPassword, String newPassword) {
		Teacher tempTeacher = teacherDAOImpl.getTeacher(username);
		if(tempTeacher != null) {
			if(checkPassword(username, oldPassword)) {
				tempTeacher.setPassword(newPassword);
				return teacherDAOImpl.update(username, tempTeacher);
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param username 账号用户名
	 * @param school 学校
	 * @param teacherNumber 教工号
	 * @param department 院系
	 * @return 存在对应账号返回teacherDAOImpl.update(username, tempTeacher)的结果，否则返回False
	 */
	public boolean updateSchoolInformation(String username, String school, String teacherNumber, String department) {
		Teacher tempTeacher = teacherDAOImpl.getTeacher(username);
		if(tempTeacher != null) {
			tempTeacher.setSchool(school);
			tempTeacher.setTeacherNumber(teacherNumber);
			tempTeacher.setDepartment(department);
			return teacherDAOImpl.update(username, tempTeacher);
		}
		else {
			return false;
		}
	}
}
