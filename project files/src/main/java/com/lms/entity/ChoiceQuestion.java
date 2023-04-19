package com.lms.entity;

public class ChoiceQuestion {
	private int questionId;
	private int assignment;
	private String description;
	private String choiceA;
	private String choiceB;
	private String choiceC;
	private String choiceD;
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
	public String getChoiceA() {
		return choiceA;
	}
	public void setChoiceA(String choiceA) {
		this.choiceA = choiceA;
	}
	public String getChoiceB() {
		return choiceB;
	}
	public void setChoiceB(String choiceB) {
		this.choiceB = choiceB;
	}
	public String getChoiceC() {
		return choiceC;
	}
	public void setChoiceC(String choiceC) {
		this.choiceC = choiceC;
	}
	public String getChoiceD() {
		return choiceD;
	}
	public void setChoiceD(String choiceD) {
		this.choiceD = choiceD;
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
