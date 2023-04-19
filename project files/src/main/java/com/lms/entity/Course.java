package com.lms.entity;

import java.util.Date;

public class Course {
	private String courseName;
	private String teacher;
	private int courseId;
	private Date startDate;
	private Date endDate;
	private int joinMode;
	public Course() {
		this.courseName = null;
		this.teacher = null;
		this.courseId = 0;
		this.startDate = null;
		this.endDate = null;
		this.joinMode = 0;
	}
	public Course(String courseName, String teacher, int courseId, Date startDate, Date endDate, int joinMode) {
		super();
		this.courseName = courseName;
		this.teacher = teacher;
		this.courseId = courseId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.joinMode = joinMode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getJoinMode() {
		return joinMode;
	}
	public void setJoinMode(int joinMode) {
		this.joinMode = joinMode;
	}
}
