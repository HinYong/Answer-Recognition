package com.lms.entity;

public class WordLocation {
	private int top;
	private int left;
	private int height;
	private int width;
	public WordLocation(int top, int left, int width, int height) {
		super();
		this.top = top;
		this.left = left;
		this.height = height;
		this.width = width;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void show() {
		System.out.println(top + "," + left + "," + height + ',' + width);
	}
}
