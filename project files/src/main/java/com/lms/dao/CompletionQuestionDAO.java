package com.lms.dao;

import java.util.List;

import com.lms.entity.CompletionQuestion;

public interface CompletionQuestionDAO {
	/**
	 * @param question 填空题对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(CompletionQuestion question);
	
	/**
	 * @param questionId 填空题ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int questionId);
	
	/**
	 * @param questionId 填空题ID
	 * @param question 填空题对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(int questionId, CompletionQuestion question);
	
	/**
	 * @param questionId 填空题ID
	 * @return 获取到的填空题对象
	 */
	CompletionQuestion getCompletionQuestion(int questionId);
	
	/**
	 * @param assignment 作业ID
	 * @return 获取到的填空题列表
	 */
	List<CompletionQuestion> getQuestions(int assignment);
}
