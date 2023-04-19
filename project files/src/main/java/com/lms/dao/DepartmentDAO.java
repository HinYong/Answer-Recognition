package com.lms.dao;

public interface DepartmentDAO {
	/**
	 * @param name 院系名
	 * @return 操作成功返回True，否则返回False
	 */
	public boolean add(String name);
	
	/**
	 * @param departmentId 院系ID
	 * @return 获取到的院系名
	 */
	public String getName(int departmentId);
	
	/**
	 * @param name 院系名
	 * @return 获取到的院系ID
	 */
	public int getDepartmentId(String name);
}
