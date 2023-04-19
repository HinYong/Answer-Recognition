package com.lms.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.TeacherDAO;
import com.lms.entity.Teacher;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private SchoolDAOImpl schoolDAOImpl;
	@Autowired
	private DepartmentDAOImpl departmentDAOImpl;
	
	@Override
	public boolean add(String username, String password) {
		String sql = "insert into teacher (username, password) values (?, ?);";
		try {
			if(jdbc.update(sql, username, password) > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public boolean add(Teacher teacher) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean delete(String username) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean update(String username, Teacher teacher) {
		String sql = "update teacher set password = ?, realname = ?, school = ?, teacherNumber = ?, "
				+ "department = ?, phone = ?, email = ? where username = ?;";
		try {
			schoolDAOImpl.add(teacher.getSchool());
			departmentDAOImpl.add(teacher.getDepartment());
			if(jdbc.update(sql, teacher.getPassword(), teacher.getRealname(), schoolDAOImpl.getSchoolId(teacher.getSchool()), 
					teacher.getTeacherNumber(), departmentDAOImpl.getDepartmentId(teacher.getDepartment()), teacher.getPhone(), teacher.getEmail(), username) > 0){
				return true;
			} 
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Teacher getTeacher(String username) {
		String sql = "select * from teacher where username = ?";
		try {
		 	Map<String, Object> map = jdbc.queryForMap(sql, username);
		 	return mapToTeacher(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Teacher getTeacher(int teacherId) {
		String sql = "select * from teacher where teacherId = ?";
		try {
		 	Map<String, Object> map = jdbc.queryForMap(sql, teacherId);
		 	return mapToTeacher(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Teacher mapToTeacher(Map<String, Object> map) {
		Teacher teacher = new Teacher();
		
		if(map.get("username") != null) {
			teacher.setUsername(map.get("username").toString());
		}
		if(map.get("teacherId") != null) {
			teacher.setTeacherId(Integer.parseInt(map.get("teacherId").toString()));
		}
	 	if(map.get("password") != null) {
	 		teacher.setPassword(map.get("password").toString());
	 	}
	 	if(map.get("realname") != null) {
	 		teacher.setRealname(map.get("realname").toString());
	 	}
	 	if(map.get("school") != null) {
	 		int schoolId = Integer.parseInt(map.get("school").toString());
	 		teacher.setSchool(schoolDAOImpl.getName(schoolId));
	 	}
		if(map.get("teacherNumber") != null) {
			teacher.setTeacherNumber(map.get("teacherNumber").toString()); 		
		}
		if(map.get("department") != null) {
			int departmentId = Integer.parseInt(map.get("department").toString());
			teacher.setDepartment(departmentDAOImpl.getName(departmentId));
		}
		if(map.get("phone") != null) {
			teacher.setPhone(map.get("phone").toString());
		}
		if(map.get("email") != null) {
			teacher.setEmail(map.get("email").toString());
		}

	 	return teacher;
	}

}
