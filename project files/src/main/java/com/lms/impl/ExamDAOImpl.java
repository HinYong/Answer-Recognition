package com.lms.impl;

import com.lms.dao.ExamDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExamDAOImpl implements ExamDAO {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public boolean updategrade(String studentNumber, int courseId, double grade) {
        String sql = "update exam set grade=? where studentNumber=? and courseId=?;";
        try {
            if(jdbc.update(sql, grade, studentNumber, courseId) > 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean insertgrade(String studentNumber, int courseId, double grade) {
        String sql = "insert into exam (studentNumber, courseId, grade) values (?, ?, ?);";
        try {
            if(jdbc.update(sql, studentNumber, courseId, grade) > 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
