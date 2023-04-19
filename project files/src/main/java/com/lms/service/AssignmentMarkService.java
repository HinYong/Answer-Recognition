package com.lms.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.AssignmentMark;
import com.lms.entity.ChoiceQuestion;
import com.lms.entity.CompletionQuestion;
import com.lms.entity.Student;
import com.lms.impl.AssignmentMarkDAOImpl;
import com.lms.impl.ChoiceQuestionDAOImpl;
import com.lms.impl.CompletionQuestionDAOImpl;
import com.lms.impl.StudentDAOImpl;

@Service
public class AssignmentMarkService {
	@Autowired
	private AssignmentMarkDAOImpl assignmentMarkDAOImpl;
	@Autowired
	private ChoiceQuestionDAOImpl choiceQuestionDAOImpl;
	@Autowired
	private CompletionQuestionDAOImpl completionQuestionDAOImpl;
	@Autowired
	private StudentDAOImpl studentDAOImpl;
	
	/**
	 * @param jsonData json形式的答案数据内容
	 * @param student 学生用户名
	 * @return assignmentMarkDAOImpl.add(assignmentMark)的结果
	 */
	public boolean calculateMark(String jsonData, String student) {
		JSONArray jsonArray = new JSONArray(jsonData);
		AssignmentMark assignmentMark = new AssignmentMark();
		Student tempStudent = studentDAOImpl.getStudent(student);
		int assignmentId = jsonArray.getJSONObject(0).getInt("assignmentId");
		int mark = 0;
		for(int i = 1;i<jsonArray.length();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(jsonObject.getString("type").equals("choice")) {
				ChoiceQuestion question = choiceQuestionDAOImpl.getChoiceQuestion(jsonObject.getInt("questionId"));				
				if(jsonObject.getString("answer").equals(question.getAnswer())) {
					mark += question.getMark();
				}
			} else if(jsonObject.getString("type").equals("completion")) {
				CompletionQuestion question = completionQuestionDAOImpl.getCompletionQuestion(jsonObject.getInt("questionId"));
				if(jsonObject.getString("answer").equals(question.getAnswer())) {
					mark += question.getMark();
				}
			} else {
				return false;
			}
		}
		assignmentMark.setAssignment(assignmentId);
		assignmentMark.setStudent(tempStudent.getStudentId());
		assignmentMark.setMark(mark);
		return assignmentMarkDAOImpl.add(assignmentMark);
	}
	
	/**
	 * @param assignment 作业ID
	 * @param student 学生用户名
	 * @return 获取到成绩返回assignmentMark.getMark()的结果，否则返回-1
	 */
	public int getMark(int assignment, String student) {
		Student tempStudent = studentDAOImpl.getStudent(student);
		AssignmentMark assignmentMark = assignmentMarkDAOImpl.getAssignmentMark(assignment, tempStudent.getStudentId());
		if(assignmentMark != null) {
			return assignmentMark.getMark();
		} else {
			return -1;
		}
	}
	
	/**
	 * @param course 课程ID
	 * @param student 学生用户名
	 * @return assignmentMarkDAOImpl.getStudentMark(course, tempStudent.getStudentId())的结果
	 */
	public double getStudentMark(int course, String student) {
		Student tempStudent = studentDAOImpl.getStudent(student);
		return assignmentMarkDAOImpl.getStudentMark(course, tempStudent.getStudentId());
	}
}
