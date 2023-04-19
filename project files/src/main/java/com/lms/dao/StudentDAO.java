package com.lms.dao;

import com.lms.entity.Student;

public interface StudentDAO {
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(String username, String password);
	
	/**
	 * @param student 学生对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(Student student);
	
	/**
	 * @param username 账号用户名
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(String username);
	
	/**
	 * @param username 账号用户名
	 * @param student 学生对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(String username, Student student);
	
	/**
	 * @param username 账号用户名
	 * @return 获取到的学生对象
	 */
	Student getStudent(String username);
	
}
