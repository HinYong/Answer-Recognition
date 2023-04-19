package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.ChoiceQuestion;
import com.lms.impl.ChoiceQuestionDAOImpl;

@Service
public class ChoiceQuestionService {
	@Autowired
	private ChoiceQuestionDAOImpl questionDAOImpl;
	
	/**
	 * @param question 选择题对象
	 * @return questionDAOImpl.add(question)的结果
	 */
	public boolean addQuestion(ChoiceQuestion question) {
		return questionDAOImpl.add(question);
	}
	
	/**
	 * @param questionId 选择题ID
	 * @return questionDAOImpl.delete(questionId)的结果
	 */
	public boolean deleteQuestion(int questionId) {
		return questionDAOImpl.delete(questionId);
	}
	
	/** 
	 * @param questionId 选择题ID
	 * @param question 选择题对象
	 * @return questionDAOImpl.update(questionId, question)的结果
	 */
	public boolean updateQuestion(int questionId, ChoiceQuestion question) {
		return questionDAOImpl.update(questionId, question);
	}
	
	/**
	 * @param assignment 作业ID
	 * @return questionDAOImpl.getQuestions(assignment)的结果
	 */
	public List<ChoiceQuestion> getQuestions(int assignment) {
		return questionDAOImpl.getQuestions(assignment);
	}
}
