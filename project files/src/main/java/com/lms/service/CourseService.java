package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.Course;
import com.lms.entity.Student;
import com.lms.entity.Teacher;
import com.lms.impl.CourseDAOImpl;
import com.lms.impl.StudentCourseDAOImpl;
import com.lms.impl.StudentDAOImpl;
import com.lms.impl.TeacherDAOImpl;

@Service
public class CourseService {
	@Autowired
	private CourseDAOImpl courseDAOImpl;
	@Autowired
	private StudentCourseDAOImpl studentCourseDAOImpl;
	@Autowired
	private StudentDAOImpl studentDAOImpl;
	@Autowired
	private TeacherDAOImpl teacherDAOImpl;
	
	/**
	 * @param course 课程对象
	 * @return courseDAOImpl.add(course)的结果
	 */
	public boolean addCourse(Course course) {
		return courseDAOImpl.add(course);
	}
	
	/**
	 * @param student 学生用户名
	 * @param course 课程ID
	 * @return studentCourseDAOImpl.add(tempStudent.getStudentId(), course)的结果
	 */
	public boolean selectCourse(String student, int course) {
		Student tempStudent = studentDAOImpl.getStudent(student);
		return studentCourseDAOImpl.add(tempStudent.getStudentId(), course);
	}
	
	/**
	 * @param course 课程对象
	 * @return courseDAOImpl.update(course.getCourseId(), course)的结果
	 */
	public boolean updateCourse(Course course) {
		return courseDAOImpl.update(course.getCourseId(), course);
	}
	
	/**
	 * @param courseId 课程ID
	 * @return courseDAOImpl.getCourse(courseId)的结果
	 */
	public Course getCourse(int courseId) {
		return courseDAOImpl.getCourse(courseId);
	}
	
	/**
	 * @param username 学生用户名
	 * @return 获取到的课程列表
	 */
	public List<Course> getCourseListForStudent(String username) {
		Student tempStudent = studentDAOImpl.getStudent(username);
		List<Course> list = studentCourseDAOImpl.getCoursesByStudent(tempStudent.getStudentId());
		for (Course course : list) {	
			//将course中的教师属性设置为教师的姓名
			Teacher tempTeacher = teacherDAOImpl.getTeacher(Integer.parseInt(course.getTeacher()));
			course.setTeacher(tempTeacher.getRealname());
		}
		return list; 
	}
	
	/**
	 * @param username 教师用户们
	 * @return 获取到的课程列表
	 */
	public List<Course> getCourseListForTeacher(String username) {
		Teacher tempTeacher = teacherDAOImpl.getTeacher(username);
		List<Course> list = courseDAOImpl.getCoursesByTeahcer(tempTeacher.getTeacherId());
		for (Course course : list) {
			//将course中的教师属性设置为教师的姓名
			course.setTeacher(tempTeacher.getRealname());
		}
		return list; 
	}
}
