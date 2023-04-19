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

import com.lms.entity.CompletionQuestion;
import com.lms.service.CompletionQuestionService;

@CrossOrigin
@RestController
@RequestMapping("/completionQuestion")
public class CompletionQuestionController {
	@Autowired
	private CompletionQuestionService questionService;
	
	/**
	 * @param assignment 填空题所属的作业的ID
	 * @param description 填空题的描述
	 * @param answer 填空题的答案
	 * @param mark 填空题的分数
	 * @return questionService.addQuestion(question)的结果
	 */
	@PutMapping
	@ResponseBody
	public Object addQuestion(@RequestParam(value = "assignment") int assignment,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "answer") String answer,
			@RequestParam(value = "mark") int mark) {
		CompletionQuestion question = new CompletionQuestion();
		question.setAssignment(assignment);
		question.setDescription(description);
		question.setAnswer(answer);
		question.setMark(mark);
		
		return questionService.addQuestion(question);
	}
	
	/**
	 * @param questionId 填空题的ID
	 * @return questionService.deleteQuestion(questionId)的结果 
	 */
	@DeleteMapping
	@ResponseBody
	public Object deleteQuestion(@RequestParam(value = "questionId") int questionId) {
		return questionService.deleteQuestion(questionId);
	}
	
	/**
	 * @param questionId 填空题的ID
	 * @param description 填空题的描述
	 * @param answer 填空题的答案
	 * @param mark 填空题的分数
	 * @return questionService.updateQuestion(questionId, question)的结果
	 */
	@PostMapping
	@ResponseBody
	public Object updateQuestion(@RequestParam(value = "questionId") int questionId,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "answer") String answer,
			@RequestParam(value = "mark") int mark) {
		CompletionQuestion question = new CompletionQuestion();
		question.setDescription(description);
		question.setAnswer(answer);
		question.setMark(mark);
		
		return questionService.updateQuestion(questionId, question);
	}
	
	/**
	 * @param assignment 作业的ID
	 * @return questionService.getQuestions(assignment)的结果
	 */
	@GetMapping
	@ResponseBody
	public Object getQuestions(@RequestParam(value = "id") int assignment) {
		return questionService.getQuestions(assignment);
	}
}
