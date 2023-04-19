package com.lms.entity;

public class Message {
	private boolean success;
	private String text;
	public Message(boolean success, String text) {
		this.success = success;
		this.text = text;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
