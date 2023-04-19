package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.Student;
import com.lms.impl.StudentCourseDAOImpl;
import com.lms.impl.StudentDAOImpl;

@Service
public class StudentService {
	@Autowired
	private StudentDAOImpl studentDAOImpl;
	@Autowired
	private StudentCourseDAOImpl studentCourseDAOImpl;
	
	/**
	 * @param username 账号用户名
	 * @return 存在此用户名返回True，否则返回False
	 */
	public boolean checkUsernameExist(String username) {
		return studentDAOImpl.getStudent(username) != null ? true : false;
	}
	
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return studentDAOImpl.add(username, password)的结果
	 */
	public boolean register(String username, String password) {
		return studentDAOImpl.add(username, password);
	}	
	
	/**
	 * @param username 账号用户名
	 * @return studentDAOImpl.getStudent(username)的结果
	 */
	public Student getStudent(String username) {
		return studentDAOImpl.getStudent(username);
	}
	
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 用户名和密码正确匹配返回True，否则返回False
	 */
	public boolean checkPassword(String username, String password) {
		Student tempStudent = studentDAOImpl.getStudent(username);
		if(tempStudent != null) {
			if(password.equals(tempStudent.getPassword())) {
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
	 * @return 存在对应账号返回studentDAOImpl.update(username, tempStudent)的结果，否则返回False
	 */
	public boolean updateInformation(String username, String realname, String phone, String email) {
		Student tempStudent = studentDAOImpl.getStudent(username);
		if(tempStudent != null) {
			tempStudent.setRealname(realname);
			tempStudent.setPhone(phone);
			tempStudent.setEmail(email);
			return studentDAOImpl.update(username, tempStudent);
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param username 账号用户名
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @return 账号密码正确匹配则返回studentDAOImpl.update(username, tempStudent)的结果，否则返回Fasle
	 */
	public boolean updatePassword(String username, String oldPassword, String newPassword) {
		Student tempStudent = studentDAOImpl.getStudent(username);
		if(tempStudent != null) {
			if(checkPassword(username, oldPassword)) {
				tempStudent.setPassword(newPassword);
				return studentDAOImpl.update(username, tempStudent);
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
	 * @param school 学校名
	 * @param studentNumber 学号
	 * @param department 院系
	 * @param schoolClass 课程
	 * @return 存在对应账号则返回studentDAOImpl.update(username, tempStudent)的结果，否则返回False
	 */
	public boolean updateSchoolInformation(String username, String school, String studentNumber, String department, String schoolClass) {
		Student tempStudent = studentDAOImpl.getStudent(username);
		if(tempStudent != null) {
			tempStudent.setSchool(school);
			tempStudent.setStudentNumber(studentNumber);
			tempStudent.setDepartment(department);
			tempStudent.setSchoolClass(schoolClass);
			return studentDAOImpl.update(username, tempStudent);
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param course 课程ID
	 * @return studentCourseDAOImpl.getStudentsByCourse(course)的结果
	 */
	public List<Student> getStudentByCourse(int course){
		return studentCourseDAOImpl.getStudentsByCourse(course);
	}
}
