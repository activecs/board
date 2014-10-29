package com.kharkiv.board.service;

import java.util.List;

import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.user.User;

public interface CommentService {

	Comment addComment(Comment comment);

	Comment updateComment(Comment comment);

	void deleteCommentById(Integer id);

	List<Comment> getAllCommentsForUser(User user);

	List<Comment> getAllCommentsForUserByUserId(Integer userId);

	List<Comment> getAllCommentsForUserByUsername(String username);

	List<Comment> getAllCommentsForSchedule(Schedule schedule);

	List<Comment> getAllCommentsForScheduleByScheduleId(Integer scheduleId);
}
