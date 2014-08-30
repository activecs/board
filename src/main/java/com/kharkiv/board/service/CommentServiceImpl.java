package com.kharkiv.board.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.dao.CommentDao;
import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.user.User;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
	
	private static final String ERR_MESSAGE_USER_ID_CANNOT_BE_NULL = "User id cannot be null";
	private static final String ERR_MESSAGE_USER_CANNOT_BE_NULL = "User cannot be null";
	private static final String ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY = "User login cannot be empty";
	private static final String ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL = "Schedule cannot be null";
	private static final String ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL = "Schedule id cannot be null";
	private static final String ERR_COMMENT_CANNOT_BE_NULL = "Comment cannot be null";
	private static final String ERR_COMMENT_ID_CANNOT_BE_NULL = "Comment id cannot be null";
	
	@Inject
	private CommentDao commentDao;
	
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "commentServiceCache" }, key = "{#root.methodName,#user.id}")
	public List<Comment> getAllCommentsForUser(User user) {
		if(user == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_CANNOT_BE_NULL);
		return commentDao.getAllCommentsForUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "commentServiceCache" }, key = "{#root.methodName,#userId}")
	public List<Comment> getAllCommentsForUserByUserId(Integer userId) {
		if(userId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_ID_CANNOT_BE_NULL);
		return commentDao.getAllCommentsForUserByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "commentServiceCache" }, key = "{#root.methodName,#userLogin}")
	public List<Comment> getAllCommentsForUserByUserLogin(String userLogin) {
		if(isEmpty(userLogin))
			throw new IllegalArgumentException(ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY);
		return commentDao.getAllCommentsForUserByUserLogin(userLogin);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "commentServiceCache" }, key = "{#root.methodName,#schedule.id}")
	public List<Comment> getAllCommentsForSchedule(Schedule schedule) {
		if(schedule == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL);
		return commentDao.getAllCommentsForSchedule(schedule);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "commentServiceCache" }, key = "{#root.methodName,#scheduleId}")
	public List<Comment> getAllCommentsForScheduleByScheduleId(Integer scheduleId) {
		if(scheduleId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL);
		return commentDao.getAllCommentsForScheduleByScheduleId(scheduleId);
	}

	@Override
	@CacheEvict(value = { "commentServiceCache" }, condition = "#comment != null", allEntries = true, beforeInvocation = true)
	public Comment addComment(Comment comment) {
		if(comment == null)
			throw new IllegalArgumentException(ERR_COMMENT_CANNOT_BE_NULL);
		return commentDao.addComment(comment);
	}

	@Override
	@CacheEvict(value = { "commentServiceCache" }, condition = "#comment != null", allEntries = true, beforeInvocation = true)
	public Comment updateComment(Comment comment) {
		if(comment == null)
			throw new IllegalArgumentException(ERR_COMMENT_CANNOT_BE_NULL);
		return commentDao.updateComment(comment);
	}

	@Override
	@CacheEvict(value = { "commentServiceCache" }, condition = "#id != null", allEntries = true, beforeInvocation = true)
	public void deleteCommentById(Integer id) {
		if(id == null)
			throw new IllegalArgumentException(ERR_COMMENT_ID_CANNOT_BE_NULL);
		commentDao.deleteCommentById(id);
	}

}
