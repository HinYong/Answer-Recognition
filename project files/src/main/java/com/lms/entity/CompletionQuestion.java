package com.lms.entity;

public class CompletionQuestion {
	private int questionId;
	private int assignment;
	private String description;
	private String answer;
	private int mark;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getAssignment() {
		return assignment;
	}
	public void setAssignment(int assignment) {
		this.assignment = assignment;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
}
