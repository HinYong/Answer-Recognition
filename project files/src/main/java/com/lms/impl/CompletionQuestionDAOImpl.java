package com.lms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.CompletionQuestionDAO;
import com.lms.entity.CompletionQuestion;

@Repository
public class CompletionQuestionDAOImpl implements CompletionQuestionDAO {
	@Autowired
	private JdbcTemplate jdbc;
		
	@Override
	public boolean add(CompletionQuestion question) {
		String sql = "insert into completion_question (assignment, description, answer, mark) values (?,?,?,?);";
		try {
			if(jdbc.update(sql, question.getAssignment(), question.getDescription(), question.getAnswer(), question.getMark()) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int questionId) {
		String sql = "delete from completion_question where questionId = ?;";
		try {
			if(jdbc.update(sql, questionId) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(int questionId, CompletionQuestion question) {
		String sql = "update completion_question set description = ?, answer = ?, mark = ? where questionId = ?;";
		try {
			if(jdbc.update(sql, question.getDescription(), question.getAnswer(), question.getMark(), questionId) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CompletionQuestion getCompletionQuestion(int questionId) {
		String sql = "select * from completion_question where questionId = ?;";
		try {
			Map<String, Object> map = jdbc.queryForMap(sql, questionId);
			return mapToCompletionQuestion(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CompletionQuestion> getQuestions(int assignment) {
		String sql = "select * from completion_question where assignment = ?;";
		try {
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, assignment);
			List<CompletionQuestion> questionList = new ArrayList<CompletionQuestion>();
			for (Map<String, Object> map : mapList) {
				questionList.add(getCompletionQuestion(Integer.parseInt(map.get("questionId").toString())));
			}
			return questionList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CompletionQuestion mapToCompletionQuestion(Map<String, Object> map) {
		CompletionQuestion question = new CompletionQuestion();

		if (map.get("questionId") != null) {
			question.setQuestionId(Integer.parseInt(map.get("questionId").toString()));
		}
		if (map.get("assignment") != null) {
			question.setAssignment(Integer.parseInt(map.get("assignment").toString()));
		}
		if (map.get("description") != null) {
			question.setDescription(map.get("description").toString());
		}
		if (map.get("answer") != null) {
			question.setAnswer(map.get("Answer").toString());
		}
		if (map.get("mark") != null) {
			question.setMark(Integer.parseInt(map.get("mark").toString()));
		}
		
		return question;
	}
}
