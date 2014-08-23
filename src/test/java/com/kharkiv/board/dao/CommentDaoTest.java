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

    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<Comment> query;

    @Spy
    @InjectMocks
    private CommentDao commentDao = new CommentDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullUser_whenCallGetAllCommentsForUser() {
        commentDao.getAllCommentsForUser(null);
    }

    @Test
    public void shouldCallFindByUserIdAccordingGivenUser_whenCallGetAllCommentsForUser() {
        User user = new User();
        user.setId(1);
        Comment comment = new Comment();
        doReturn(Arrays.asList(comment)).when(commentDao).getAllCommentsForUserByUserId(anyInt());
        List<Comment> userComments = commentDao.getAllCommentsForUser(user);
        verify(commentDao).getAllCommentsForUserByUserId(user.getId());
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }

    @Test
    public void shouldFindAllUserCommentsAccordingGivenUserId_whenCallGetAllCommentsForUserByUserId() {
        Integer userId = 1;
        Comment comment = new Comment();
        when(query.getResultList()).thenReturn(Arrays.asList(comment));
        List<Comment> userComments = commentDao.getAllCommentsForUserByUserId(userId);
        verify(em).createNamedQuery(CommentQueries.GET_4_USER_BY_USER_ID, Comment.class);
        verify(query).setParameter("userId", userId);
        verify(query).getResultList();
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }
    
    @Test
    public void shouldFindAllUserCommentsAccordingGivenLogin_whenCallGetAllCommentsForUserByUserLogin() {
        String login = "login";
        Comment comment = new Comment();
        when(query.getResultList()).thenReturn(Arrays.asList(comment));
        List<Comment> userComments = commentDao.getAllCommentsForUserByUserLogin(login);
        verify(em).createNamedQuery(CommentQueries.GET_4_USER_BY_USER_LOGIN, Comment.class);
        verify(query).setParameter("login", login);
        verify(query).getResultList();
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullSchedule_whenCallGetAllCommentsForSchedule() {
        commentDao.getAllCommentsForSchedule(null);
    }
    
    @Test
    public void shouldCallFindByUserIdAccordingGivenSchedule_whenCallGetAllCommentsForSchedule() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        Comment comment = new Comment();
        doReturn(Arrays.asList(comment)).when(commentDao).getAllCommentsForScheduleByScheduleId(anyInt());
        List<Comment> userComments = commentDao.getAllCommentsForSchedule(schedule);
        verify(commentDao).getAllCommentsForScheduleByScheduleId(schedule.getId());
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }
    
    @Test
    public void shouldFindAllUserCommentsAccordingGivenScheduleId_whenCallGetAllCommentsForScheduleByScheduleId() {
        Integer scheduleId = 1;
        Comment comment = new Comment();
        when(query.getResultList()).thenReturn(Arrays.asList(comment));
        List<Comment> userComments = commentDao.getAllCommentsForScheduleByScheduleId(scheduleId);
        verify(em).createNamedQuery(CommentQueries.GET_4_SCHEDULE_BY_SCHEDULE_ID, Comment.class);
        verify(query).setParameter("scheduleId", scheduleId);
        verify(query).getResultList();
        assertNotNull(userComments);
        assertThat(userComments).containsOnly(comment);
    }
}
