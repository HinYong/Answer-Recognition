package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.entity.CompletionQuestion;
import com.lms.impl.CompletionQuestionDAOImpl;

@Service
public class CompletionQuestionService {
	@Autowired
	private CompletionQuestionDAOImpl questionDAOImpl;
	
	/**
	 * @param question 填空题对象
	 * @return questionDAOImpl.add(question)的结果
	 */
	public boolean addQuestion(CompletionQuestion question) {
		return questionDAOImpl.add(question);
	}
	
	/**
	 * @param questionId 填空题ID
	 * @return questionDAOImpl.delete(questionId)的结果
	 */
	public boolean deleteQuestion(int questionId) {
		return questionDAOImpl.delete(questionId);
	}
	
	/**
	 * @param questionId 填空题ID
	 * @param question 填空题对象
	 * @return questionDAOImpl.update(questionId, question)的结果 
	 */
	public boolean updateQuestion(int questionId, CompletionQuestion question) {
		return questionDAOImpl.update(questionId, question);
	}
	
	/**
	 * @param assignment 作业ID
	 * @return questionDAOImpl.getQuestions(assignment)的结果
	 */
	public List<CompletionQuestion> getQuestions(int assignment) {
		return questionDAOImpl.getQuestions(assignment);
	}
}
