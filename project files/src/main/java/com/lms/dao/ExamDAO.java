package com.lms.dao;

public interface ExamDAO {
    /**
     * @param studentNumber 学号
     * @param courseId 课程号
     * @param grade 成绩
     * @return 操作成功返回True，否则返回False
     */
    public boolean updategrade(String studentNumber,int courseId,double grade);

    public boolean insertgrade(String studentNumber,int courseId,double grade);
}
