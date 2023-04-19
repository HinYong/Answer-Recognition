package com.lms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.SchoolDAO;

@Repository
public class SchoolDAOImpl implements SchoolDAO {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public boolean add(String name) {
		String sql = "insert into school(name) value(?);";
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
	public String getName(int schoolId) {
		String sql = "select name from school where schoolId = ?;";
		try {
			return jdbc.queryForObject(sql, String.class, schoolId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int getSchoolId(String name) {
		String sql = "select schoolId from school where name = ?;";
		try {
			return jdbc.queryForObject(sql, int.class, name);
		} catch (Exception e) {
			return -1;
		}
	}

}
