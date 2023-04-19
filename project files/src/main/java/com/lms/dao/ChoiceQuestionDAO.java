package com.lms.dao;

import java.util.List;

import com.lms.entity.ChoiceQuestion;

public interface ChoiceQuestionDAO {
	/**
	 * @param question 选择题对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean add(ChoiceQuestion question);
	
	/**
	 * @param questionId 选择题ID
	 * @return 操作成功返回True，否则返回False
	 */
	boolean delete(int questionId);
	
	/**
	 * @param questionId 选择题ID
	 * @param question 选择题对象
	 * @return 操作成功返回True，否则返回False
	 */
	boolean update(int questionId, ChoiceQuestion question);
	
	/**
	 * @param questionId 选择题ID
	 * @return 获取到的选择题对象
	 */
	ChoiceQuestion getChoiceQuestion(int questionId);
	
	/**
	 * @param assignment 作业ID
	 * @return 获取到的选择题列表
	 */
	List<ChoiceQuestion> getQuestions(int assignment);
}
