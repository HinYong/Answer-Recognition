package com.lms.entity;

import java.util.Date;

public class Assignment {
	private int assignmentId;
	private int course;
	private String assignmentName;
	private float weight;
	private Date startTime;
	private Date endTime;
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	public int getCourse() {
		return course;
	}
	public void setCourse(int course) {
		this.course = course;
	}
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
