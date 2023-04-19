package com.lms.dao;

public interface SchoolDAO {
	/**
	 * @param name 学校名
	 * @return 操作成功返回True，否则返回False
	 */
	public boolean add(String name);
	
	/**
	 * @param schoolId 学校ID
	 * @return 获取到的学校名
	 */
	public String getName(int schoolId);
	
	/**
	 * @param name 学校名
	 * @return 获取到的学校ID
	 */
	public int getSchoolId(String name);
}
