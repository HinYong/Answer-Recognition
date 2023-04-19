package com.lms.dao;

import com.lms.entity.Teacher;

public interface TeacherDAO {
	/**
	 * @param username 账号用户名
	 * @param password 账号密码
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(String username, String password);
	
	/**
	 * @param teacher 教师对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(Teacher teacher);
	
	/**
	 * @param username 账号用户名
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(String username);
	
	/**
	 * @param username 账号用户名
	 * @param teacher 教师对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(String username, Teacher teacher);
	
	/**
	 * @param username 账号用户名
	 * @return 获取到的教师对象
	 */
	Teacher getTeacher(String username);
}
