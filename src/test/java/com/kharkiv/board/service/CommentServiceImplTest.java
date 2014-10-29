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
	
	private static final Integer COMMENT_ID = 21;
	private static final String USER_LOGIN = "userLogin";
	private static final Integer USER_ID = 15;
	private static final Integer SCHEDULE_ID = 16;
	
	@InjectMocks
	private CommentService service = new CommentServiceImpl();
	@Mock
	private CommentDao mockCommentDao;
	
	private User user = new User();
	private Comment comment = new Comment();
	private Schedule schedule = new Schedule();
	private List<Comment> comments = newArrayList(comment);

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		initMockBehaviour();
	}

	private void initMockBehaviour() {
		when(mockCommentDao.updateComment(comment)).thenReturn(comment);
		when(mockCommentDao.addComment(comment)).thenReturn(comment);
		when(mockCommentDao.getAllCommentsForUser(user)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForUserByUserName(USER_LOGIN)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForUserByUserId(USER_ID)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForSchedule(schedule)).thenReturn(comments);
		when(mockCommentDao.getAllCommentsForScheduleByScheduleId(SCHEDULE_ID)).thenReturn(comments);
	}

	@Test
	public void shouldCallGetAllCommentsForUserOnDaoWithGivenUser_whenCallGetAllCommentsForUser() {
		service.getAllCommentsForUser(user);
		verify(mockCommentDao).getAllCommentsForUser(user);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_whenCallGetAllCommentsForUser() {
		List<Comment> actualComments = service.getAllCommentsForUser(user);
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
		service.getAllCommentsForUserByUsername(USER_LOGIN);
		verify(mockCommentDao).getAllCommentsForUserByUserName(USER_LOGIN);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_getAllCommentsForUserByUserLogin() {
		List<Comment> actualComments = service.getAllCommentsForUserByUsername(USER_LOGIN);
		assertThat(actualComments).isEqualTo(comments);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsNullAndCallGetAllCommentsForUserByUserLogin() {
		service.getAllCommentsForUserByUsername(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsEmptyAndCallGetAllCommentsForUserByUserLogin() {
		service.getAllCommentsForUserByUsername(EMPTY);
	}
	
	@Test
	public void shouldCallGetAllCommentsForScheduleOnDaoWithGivenSchedule_whenCallGetAllCommentsForSchedule() {
		service.getAllCommentsForSchedule(schedule);
		verify(mockCommentDao).getAllCommentsForSchedule(schedule);
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_whenCallGetAllCommentsForSchedule() {
		List<Comment> actualComments = service.getAllCommentsForSchedule(schedule);
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
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenCommentIsNullAndCallGetAddComments() {
		service.addComment(null);
	}
	
	@Test
	public void shouldCallAddCommentOnDaoWithGivenComment_whenCallAddComment(){
		service.addComment(comment);
		verify(mockCommentDao).addComment(comment);
	}
	
	@Test
	public void shouldReturnCommentGivenByDao_whenCallAddComment(){
		Comment actualComment = service.addComment(comment);
		assertThat(actualComment).isEqualTo(comment);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenCommentIsNullAndCallUpdateComment() {
		service.updateComment(null);
	}
	
	@Test
	public void shouldCallUpdateCommentOnDaoWithGivenComment_whenCallUpdateComment(){
		service.updateComment(comment);
		verify(mockCommentDao).updateComment(comment);
	}
	
	@Test
	public void shouldReturnCommentGivenByDao_whenCallUpdateComment(){
		Comment actualComment = service.updateComment(comment);
		assertThat(actualComment).isEqualTo(comment);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenCommentIdIsNullAndCallDeleteCommentById() {
		service.deleteCommentById(null);
	}
	
	@Test
	public void shouldCallDeleteCommentOnDaoWithGivenCommentId_whenCallDeleteCommentById(){
		service.deleteCommentById(COMMENT_ID);
		verify(mockCommentDao).deleteCommentById(COMMENT_ID);
	}
}