package com.kharkiv.board.dao;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.CommentQueries;

public class CommentDaoTest {

    private static final Integer ID = 1;
    private static final Integer USER_ID = 1;
    private static final String USER_LOGIN = "login";
    private static final Integer SCHEDULE_ID = 1;

    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<Comment> query;

    private Comment comment;

    private User user;

    private Schedule schedule;

    @Spy
    @InjectMocks
    private CommentDao commentDao = new CommentDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        comment = new Comment();
        user = new User();
        user.setId(USER_ID);
        schedule = new Schedule();
        schedule.setId(SCHEDULE_ID);

        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullUser_whenCallGetAllCommentsForUser() {
        commentDao.getAllCommentsForUser(null);
    }

    @Test
    public void shouldCallFindByUserIdAccordingGivenUser_whenCallGetAllCommentsForUser() {
        doReturn(Arrays.asList(comment)).when(commentDao).getAllCommentsForUserByUserId(anyInt());
        List<Comment> userComments = commentDao.getAllCommentsForUser(user);
        verify(commentDao).getAllCommentsForUserByUserId(user.getId());
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }

    @Test
    public void shouldFindAllUserCommentsAccordingGivenUserId_whenCallGetAllCommentsForUserByUserId() {
        when(query.getResultList()).thenReturn(Arrays.asList(comment));
        List<Comment> userComments = commentDao.getAllCommentsForUserByUserId(USER_ID);
        verify(em).createNamedQuery(CommentQueries.GET_4_USER_BY_USER_ID, Comment.class);
        verify(query).setParameter("userId", USER_ID);
        verify(query).getResultList();
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }

    @Test
    public void shouldFindAllUserCommentsForGivenUserLogin_whenCallGetAllCommentsForUserByUserLogin() {
        when(query.getResultList()).thenReturn(Arrays.asList(comment));
        List<Comment> userComments = commentDao.getAllCommentsForUserByUserLogin(USER_LOGIN);
        verify(em).createNamedQuery(CommentQueries.GET_4_USER_BY_USER_LOGIN, Comment.class);
        verify(query).setParameter("login", USER_LOGIN);
        verify(query).getResultList();
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallGetAllCommentsForUser() {
        commentDao.getAllCommentsForSchedule(null);
    }

    @Test
    public void shouldCallMethodFindByUserIdOnDaoWithGivenUser_whenCallGetAllCommentsForSchedule() {
        doReturn(Arrays.asList(comment)).when(commentDao).getAllCommentsForScheduleByScheduleId(anyInt());
        List<Comment> userComments = commentDao.getAllCommentsForSchedule(schedule);
        verify(commentDao).getAllCommentsForScheduleByScheduleId(schedule.getId());
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }

    @Test
    public void shouldFindAllUserCommentsAccordingGivenScheduleId_whenCallGetAllCommentsForScheduleByScheduleId() {
        when(query.getResultList()).thenReturn(Arrays.asList(comment));
        List<Comment> userComments = commentDao.getAllCommentsForScheduleByScheduleId(SCHEDULE_ID);
        verify(em).createNamedQuery(CommentQueries.GET_4_SCHEDULE_BY_SCHEDULE_ID, Comment.class);
        verify(query).setParameter("scheduleId", SCHEDULE_ID);
        verify(query).getResultList();
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }
    
    @Test
    public void shouldPersistGivenComment_whenCallAddComment() {
        Comment added = commentDao.addComment(comment);
        verify(em).persist(comment);
        assertThat(added).isEqualTo(comment);
    }
    
    @Test
    public void shouldFlushPersistedComment_whenCallAddComment() {
        Comment added = commentDao.addComment(comment);
        verify(em).flush();
        assertThat(added).isEqualTo(comment);
    }
    
    @Test
    public void shouldUpdateGivenComment_whenCallUpdateComment() {
        when(em.merge(any(Comment.class))).thenReturn(comment);
        Comment updated = commentDao.updateComment(comment);
        verify(em).merge(comment);
        assertThat(updated).isSameAs(comment);
    }
    
    @Test
    public void shouldDeleteCommentAccordingGivenId_whenCallDeleteCommentById() {
        when(query.executeUpdate()).thenReturn(1);
        int deleted = commentDao.deleteCommentById(ID);
        verify(em).createNamedQuery(CommentQueries.DELETE_BY_ID, Comment.class);
        verify(query).setParameter("id", ID);
        verify(query).executeUpdate();
        assertThat(deleted).isEqualTo(1);
    }
}
