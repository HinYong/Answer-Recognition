package com.lms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.entity.Student;
import com.lms.service.AssignmentMarkService;
import com.lms.service.StudentService;

@CrossOrigin
@RestController
@RequestMapping("/studentManage")
public class StudentManageController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private AssignmentMarkService assignmentMarkService;
	
	/**
	 * @param course 课程ID
	 * @return Object列表形式的学生数据
	 */
	@GetMapping
	public Object getStudentInformation(@RequestParam(value = "id") int course) {
		List<Object> data = new ArrayList<Object>();
		List<Student> studentList = studentService.getStudentByCourse(course);
		List<Double> markList = new ArrayList<Double>();
		for (Student student : studentList) {
			markList.add(assignmentMarkService.getStudentMark(course, student.getUsername()));
		}
		data.add(studentList);
		data.add(markList);
		return data;
	}
}
