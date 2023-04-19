package com.lms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.DepartmentDAO;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public boolean add(String name) {
		String sql = "insert into department(name) value(?);";
		try {
			if(jdbc.update(sql, name) > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getName(int departmentId) {
		String sql = "select name from department where departmentId = ?;";
		try {
			return jdbc.queryForObject(sql, String.class, departmentId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int getDepartmentId(String name) {
		String sql = "select departmentId from department where name = ?;";
		try {
			return jdbc.queryForObject(sql, int.class, name);
		} catch (Exception e) {
			return -1;
		}
	}

}
