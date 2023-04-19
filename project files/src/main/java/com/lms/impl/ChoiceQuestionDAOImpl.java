package com.lms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.dao.ChoiceQuestionDAO;
import com.lms.entity.ChoiceQuestion;

@Repository
public class ChoiceQuestionDAOImpl implements ChoiceQuestionDAO {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public boolean add(ChoiceQuestion question) {
		String sql = "insert into choice_question (assignment, description, choiceA, choiceB, choiceC, choiceD, answer, mark) values (?,?,?,?,?,?,?,?);";
		try {
			if(jdbc.update(sql, question.getAssignment(), question.getDescription(), question.getChoiceA(), question.getChoiceB(), question.getChoiceC(), question.getChoiceD(), question.getAnswer(), question.getMark()) > 0) {
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
		String sql = "delete from choice_question where questionId = ?;";
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
	public boolean update(int questionId, ChoiceQuestion question) {
		String sql = "update choice_question set description = ?, choiceA = ?, choiceB = ?, choiceC = ?, choiceD = ?, answer = ?, mark = ? where questionId = ?;";
		try {
			if(jdbc.update(sql, question.getDescription(), question.getChoiceA(), question.getChoiceB(), question.getChoiceC(), question.getChoiceD(), question.getAnswer(), question.getMark(), questionId) > 0) {
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
	public ChoiceQuestion getChoiceQuestion(int questionId) {
		String sql = "select * from choice_question where questionId = ?;";
		try {
			Map<String, Object> map = jdbc.queryForMap(sql, questionId);
			return mapToChoiceQuestion(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ChoiceQuestion> getQuestions(int assignment) {
		String sql = "select * from choice_question where assignment = ?;";
		try {
			List<Map<String, Object>> mapList = jdbc.queryForList(sql, assignment);
			List<ChoiceQuestion> choiceQuestionList = new ArrayList<ChoiceQuestion>();
			for (Map<String, Object> map : mapList) {
				choiceQuestionList.add(getChoiceQuestion(Integer.parseInt(map.get("questionId").toString())));
			}
			return choiceQuestionList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ChoiceQuestion mapToChoiceQuestion(Map<String, Object> map) {
		ChoiceQuestion question = new ChoiceQuestion();

		if (map.get("questionId") != null) {
			question.setQuestionId(Integer.parseInt(map.get("questionId").toString()));
		}
		if (map.get("assignment") != null) {
			question.setAssignment(Integer.parseInt(map.get("assignment").toString()));
		}
		if (map.get("description") != null) {
			question.setDescription(map.get("description").toString());
		}
		if (map.get("choiceA") != null) {
			question.setChoiceA(map.get("choiceA").toString());
		}
		if (map.get("choiceB") != null) {
			question.setChoiceB(map.get("choiceB").toString());
		}
		if (map.get("choiceC") != null) {
			question.setChoiceC(map.get("choiceC").toString());
		}
		if (map.get("choiceD") != null) {
			question.setChoiceD(map.get("choiceD").toString());
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
