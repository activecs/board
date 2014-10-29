package com.kharkiv.board.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.CommentQueries;

@Repository("commentDao")
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getAllCommentsForUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null!");
        return getAllCommentsForUserByUserId(user.getId());
    }

    @Override
    public List<Comment> getAllCommentsForUserByUserId(Integer userId) {
        TypedQuery<Comment> query = em.createNamedQuery(CommentQueries.GET_4_USER_BY_USER_ID, Comment.class);
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Comment> getAllCommentsForUserByUserName(String username) {
        TypedQuery<Comment> query = em.createNamedQuery(CommentQueries.GET_4_USER_BY_USERNAME, Comment.class);
        return query.setParameter("username", username).getResultList();
    }

    @Override
    public List<Comment> getAllCommentsForSchedule(Schedule schedule) {
        if (schedule == null)
            throw new IllegalArgumentException("Schedule cannot be null!");
        return getAllCommentsForScheduleByScheduleId(schedule.getId());
    }

    @Override
    public List<Comment> getAllCommentsForScheduleByScheduleId(Integer scheduleId) {
        TypedQuery<Comment> query = em.createNamedQuery(CommentQueries.GET_4_SCHEDULE_BY_SCHEDULE_ID, Comment.class);
        return query.setParameter("scheduleId", scheduleId).getResultList();
    }

    @Override
    public Comment addComment(Comment comment) {
        em.persist(comment);
        em.flush();
        return comment;
    }

    @Override
    public Comment updateComment(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public int deleteCommentById(Integer id) {
        TypedQuery<Comment> query = em.createNamedQuery(CommentQueries.DELETE_BY_ID, Comment.class);
        return query.setParameter("id", id).executeUpdate();
    }

}
