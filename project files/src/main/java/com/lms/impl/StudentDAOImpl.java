package com.lms.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.StudentDAO;
import com.lms.entity.Student;

@Repository
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private SchoolDAOImpl schoolDAOImpl;
	@Autowired
	private DepartmentDAOImpl departmentDAOImpl;
	
	@Override
	public boolean add(String username, String password) {
		String sql = "insert into student (username, password) values (?, ?);";
		try {
			if(jdbc.update(sql, username, password) > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean add(Student student) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(String username, Student student) {
		String sql = "update student set password = ?, realname = ?, school = ?, studentNumber = ?, "
				+ "department = ?, schoolClass = ?, phone = ?, email = ? where username = ?;";
		try {
			schoolDAOImpl.add(student.getSchool());
			departmentDAOImpl.add(student.getDepartment());
			if(jdbc.update(sql, student.getPassword(), student.getRealname(), schoolDAOImpl.getSchoolId(student.getSchool()), student.getStudentNumber(),
					departmentDAOImpl.getDepartmentId(student.getDepartment()), student.getSchoolClass(), student.getPhone(), student.getEmail(), username) > 0){
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
	public Student getStudent(String username) {
		String sql = "select * from student where username = ?";
		try {
		 	Map<String, Object> map = jdbc.queryForMap(sql, username);
		 	return mapToStudent(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Student getStudent(int studentId) {
		String sql = "select * from student where studentId = ?";
		try {
		 	Map<String, Object> map = jdbc.queryForMap(sql, studentId);
		 	return mapToStudent(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public Student mapToStudent(Map<String, Object> map) {
		Student student = new Student();
		
		if(map.get("username") != null) {
			student.setUsername(map.get("username").toString());
		}
		if(map.get("studentId") != null) {
			student.setStudentId(Integer.parseInt(map.get("studentId").toString()));
		}
	 	if(map.get("password") != null) {
	 		student.setPassword(map.get("password").toString());
	 	}
	 	if(map.get("realname") != null) {
	 		student.setRealname(map.get("realname").toString());
	 	}
	 	if(map.get("school") != null) {
	 		int schoolId = Integer.parseInt(map.get("school").toString());
	 		student.setSchool(schoolDAOImpl.getName(schoolId));
	 	}
		if(map.get("studentNumber") != null) {
			student.setStudentNumber(map.get("studentNumber").toString()); 		
		}
		if(map.get("department") != null) {
			int departmentId = Integer.parseInt(map.get("department").toString());
	 		student.setDepartment(departmentDAOImpl.getName(departmentId));
		}
		if(map.get("schoolClass") != null) {
	 		student.setSchoolClass(map.get("schoolClass").toString());
		}
		if(map.get("phone") != null) {
	 		student.setPhone(map.get("phone").toString());
		}
		if(map.get("email") != null) {
	 		student.setEmail(map.get("email").toString());
		}
	 	
	 	return student;
	}
}
