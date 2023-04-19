package com.lms.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.Student;
import com.lms.impl.StudentCourseLogDAOImpl;
import com.lms.impl.StudentDAOImpl;

@Service
public class StudentCourseLogService {
	@Autowired
	private StudentDAOImpl studentDAOImpl;
	@Autowired
	private StudentCourseLogDAOImpl studentCourseLogDAOImpl;
	
	/**
	 * @param student 学生用户名
	 * @param course 课程ID
	 * @param data Log内容
	 * @return 创建成功返回True，否则返回False
	 */
	public boolean createLog(String student, int course, String data) {
		//声明目标log文件
		String file = "src/main/webapp/log/" + student + "_" + course + "_log.txt";
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			try {
				//创建log文件并写入数据
				fileOutputStream.write(data.getBytes());
				//将log文件路径保存至数据库
				Student tempStudent = studentDAOImpl.getStudent(student);
				studentCourseLogDAOImpl.add(tempStudent.getStudentId(), course, student + "_" + course + "_log.txt");
				fileOutputStream.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @param student 学生用户名
	 * @param course 课程ID
	 * @param data log内容
	 * @return 更新成功返回True，否则返回False
	 */
	public boolean updateLog(String student, int course, String data) {
		Student tempStudent = studentDAOImpl.getStudent(student);
		String file = studentCourseLogDAOImpl.getLog(tempStudent.getStudentId(), course);
		file = "src/main/webapp/log/" + file;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file,true);
			try {
				//在log文件新的一行追加写入数据
				data = '\n' + data;
				fileOutputStream.write(data.getBytes());
				fileOutputStream.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @param student 学生用户名
	 * @param course 课程ID
	 * @param data log内容
	 * @return createLog(student, course, data)或updateLog(student, course, data)的结果
	 */
	public boolean receiveLog(String student, int course, String data) {
		Student tempStudent = studentDAOImpl.getStudent(student);
		if(studentCourseLogDAOImpl.getLog(tempStudent.getStudentId(), course) == null) {
			return createLog(student, course, data);
		}
		else {
			return updateLog(student, course, data);
		}
	}
	
	/**
	 * @param student 学生用户名
	 * @param course 课程ID
	 * @return  studentCourseLogDAOImpl.getLog(tempStudent.getStudentId(), course)的结果
	 */
	public String getLog(String student, int course) {
		Student tempStudent = studentDAOImpl.getStudent(student);
		return studentCourseLogDAOImpl.getLog(tempStudent.getStudentId(), course);
	}
	
	/**
	 * @param course 课程ID
	 * @return studentCourseLogDAOImpl.getCourseLog(course)的结果
	 */
	public List<String> getCourseLog(int course) {
		return studentCourseLogDAOImpl.getCourseLog(course);
	}
	
	/**
	 * @param log log路径
	 * @return 读取得到的log内容
	 */
	public List<String> readLog(String log) {
		try {
			List<String> logData = new ArrayList<String>();
			FileReader fileReader = new FileReader("src/main/webapp/log/" + log);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String tempLine;
			//按行读取log文件数据并存入logData中
			tempLine = bufferedReader.readLine();
			while(tempLine != null) {
				logData.add(tempLine);
				tempLine = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
			
			return logData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
