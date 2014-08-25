package com.kharkiv.board.service;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.kharkiv.board.dao.CommentDao;
import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.user.User;

public class CommentServiceImplTest {
	
	private static final String USER_LOGIN = "userLogin";
	private static final Integer USER_ID = 15;
	private static final Integer SCHEDULE_ID = 16;
	@InjectMocks
	private CommentService service = new CommentServiceImpl();
	@Mock
	private CommentDao mockCommentDao;
	@Mock
	private User mockUser;
	@Mock
	private Comment mockComment;
	@Mock
	private Schedule mockSchedule;
	
	private List<Comment> comments;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		comments = newArrayList(mockComment);
		initMockBehaviour();
	}

	private void initMockBehaviour() {
		when(mockCommentDao.getAllCommentsForUser(mockUser)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForUserByUserLogin(USER_LOGIN)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForUserByUserId(USER_ID)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForSchedule(mockSchedule)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForScheduleByScheduleId(SCHEDULE_ID)).thenReturn(comments);
	}

	@Test
	public void shouldCallGetAllCommentsForUserOnDaoWithGivenUser_whenCallGetAllCommentsForUser() {
		service.getAllCommentsForUser(mockUser);
		verify(mockCommentDao).getAllCommentsForUser(mockUser);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_whenCallGetAllCommentsForUser() {
		List<Comment> actualComments = service.getAllCommentsForUser(mockUser);
		assertThat(actualComments).isEqualTo(comments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallGetUserById() {
		service.getAllCommentsForUser(null);
	}
	
	@Test
	public void shouldCallGetAllCommentsForUserOnDaoWithGivenUser_getAllCommentsForUserByUserId() {
		service.getAllCommentsForUserByUserId(USER_ID);
		verify(mockCommentDao).getAllCommentsForUserByUserId(USER_ID);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_getAllCommentsForUserByUserId() {
		List<Comment> actualComments = service.getAllCommentsForUserByUserId(USER_ID);
		assertThat(actualComments).isEqualTo(comments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIdIsNullAndCallGetAllCommentsForUserByUserId() {
		service.getAllCommentsForUserByUserId(null);
	}
	
	@Test
	public void shouldCallGetAllCommentsForUserOnDaoWithGivenUserLogin_getAllCommentsForUserByUserLogin() {
		service.getAllCommentsForUserByUserLogin(USER_LOGIN);
		verify(mockCommentDao).getAllCommentsForUserByUserLogin(USER_LOGIN);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_getAllCommentsForUserByUserLogin() {
		List<Comment> actualComments = service.getAllCommentsForUserByUserLogin(USER_LOGIN);
		assertThat(actualComments).isEqualTo(comments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsNullAndCallGetAllCommentsForUserByUserLogin() {
		service.getAllCommentsForUserByUserLogin(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsEmptyAndCallGetAllCommentsForUserByUserLogin() {
		service.getAllCommentsForUserByUserLogin(EMPTY);
	}
	
	@Test
	public void shouldCallGetAllCommentsForScheduleOnDaoWithGivenSchedule_whenCallGetAllCommentsForSchedule() {
		service.getAllCommentsForSchedule(mockSchedule);
		verify(mockCommentDao).getAllCommentsForSchedule(mockSchedule);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_whenCallGetAllCommentsForSchedule() {
		List<Comment> actualComments = service.getAllCommentsForSchedule(mockSchedule);
		assertThat(actualComments).isEqualTo(comments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallGetAllCommentsForSchedule() {
		service.getAllCommentsForSchedule(null);
	}
	
	@Test
	public void shouldCallGetAllCommentsForScheduleByScheduleIdOnDaoWithGivenUser_whenGetAllCommentsForScheduleByScheduleId() {
		service.getAllCommentsForScheduleByScheduleId(SCHEDULE_ID);
		verify(mockCommentDao).getAllCommentsForScheduleByScheduleId(SCHEDULE_ID);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_whenGetAllCommentsForScheduleByScheduleId() {
		List<Comment> actualComments = service.getAllCommentsForScheduleByScheduleId(SCHEDULE_ID);
		assertThat(actualComments).isEqualTo(comments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenScheduleIdIsNullAndCallGetAllCommentsForScheduleByScheduleId() {
		service.getAllCommentsForScheduleByScheduleId(null);
	}
}
