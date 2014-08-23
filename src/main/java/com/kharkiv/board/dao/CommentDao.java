package com.kharkiv.board.dao;

import java.util.List;

import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.user.User;

public interface CommentDao {

    List<Comment> getAllCommentsForUser(User user);
    
    List<Comment> getAllCommentsForUserByUserId(Integer userId);
    
    List<Comment> getAllCommentsForUserByUserLogin(String userLogin);
    
    List<Comment> getAllCommentsForSchedule(Schedule schedule);
    
    List<Comment> getAllCommentsForScheduleByScheduleId(Integer scheduleId);
    
}