package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.entity.ChoiceQuestion;
import com.lms.service.ChoiceQuestionService;

@CrossOrigin
@RestController
@RequestMapping("/choiceQuestion")
public class ChoiceQuestionController {
	@Autowired
	private ChoiceQuestionService questionService;
	
	/**
	 * @param assignment 选择题所属的作业的ID
	 * @param description 选择题的描述
	 * @param choiceA A选项
	 * @param choiceB B选项
	 * @param choiceC C选项
	 * @param choiceD D选项
	 * @param answer 选择题的答案
	 * @param mark 选择题的分数
	 * @return questionService.addQuestion(question)的结果
	 */
	@PutMapping
	@ResponseBody
	public Object addQuestion(@RequestParam(value = "assignment") int assignment,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "choiceA") String choiceA,
			@RequestParam(value = "choiceB") String choiceB,
			@RequestParam(value = "choiceC") String choiceC,
			@RequestParam(value = "choiceD") String choiceD,
			@RequestParam(value = "answer") String answer,
			@RequestParam(value = "mark") int mark) {
		ChoiceQuestion question = new ChoiceQuestion();
		question.setAssignment(assignment);
		question.setDescription(description);
		question.setChoiceA(choiceA);
		question.setChoiceB(choiceB);
		question.setChoiceC(choiceC);
		question.setChoiceD(choiceD);
		question.setAnswer(answer);
		question.setMark(mark);
		
		return questionService.addQuestion(question);
	}
	
	/**
	 * @param questionId 选择题的ID
	 * @return questionService.deleteQuestion(questionId)的结果
	 */
	@DeleteMapping
	@ResponseBody
	public Object deleteQuestion(@RequestParam(value = "questionId") int questionId) {
		return questionService.deleteQuestion(questionId);
	}
	
	/**
	 * @param questionId 选择题的ID
	 * @param description 选择题的描述
	 * @param choiceA A选项
	 * @param choiceB B选项
	 * @param choiceC C选项
	 * @param choiceD D选项
	 * @param answer 选择题的答案
	 * @param mark 选择题的分数
	 * @return questionService.updateQuestion(questionId, question)的结果
	 */
	@PostMapping
	@ResponseBody
	public Object updateQuestion(@RequestParam(value = "questionId") int questionId,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "choiceA") String choiceA,
			@RequestParam(value = "choiceB") String choiceB,
			@RequestParam(value = "choiceC") String choiceC,
			@RequestParam(value = "choiceD") String choiceD,
			@RequestParam(value = "answer") String answer,
			@RequestParam(value = "mark") int mark) {
		ChoiceQuestion question = new ChoiceQuestion();
		question.setDescription(description);
		question.setChoiceA(choiceA);
		question.setChoiceB(choiceB);
		question.setChoiceC(choiceC);
		question.setChoiceD(choiceD);
		question.setAnswer(answer);
		question.setMark(mark);
		
		return questionService.updateQuestion(questionId, question);
	}
	
	/**
	 * @param assignment 所属作业的ID
	 * @return questionService.getQuestions(assignment)的结果
	 */
	@GetMapping
	@ResponseBody
	public Object getQuestions(@RequestParam(value = "id") int assignment) {
		return questionService.getQuestions(assignment);
	}
}
