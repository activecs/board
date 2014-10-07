package com.kharkiv.board.service;

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

import com.google.common.collect.Lists;
import com.kharkiv.board.dao.UserDao;
import com.kharkiv.board.dto.user.User;

public class UserServiceImplTest {
	
	private static final String USER_LOGIN = "userLogin";
	private static final Integer USER_ID = 15;
	
	@InjectMocks
	private UserService service = new UserServiceImpl();
	@Mock
	private UserDao mockUserDao;
	@Mock
	private User mockCurrentUser;
	
	private User user = new User();
	private List<User> users = Lists.newArrayList(user);
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		initMockBehaviour();
	}

	private void initMockBehaviour() {
		when(mockUserDao.getAllUsers()).thenReturn(users);
		when(mockUserDao.getUserById(USER_ID)).thenReturn(user);
		when(mockUserDao.getUserByLogin(USER_LOGIN)).thenReturn(user);
		when(mockUserDao.addUser(user)).thenReturn(user);
		when(mockUserDao.updateUser(user)).thenReturn(user);
	}

	@Test
	public void shouldCallGetUserByIdOnDaoWithGivenUserId_whenCallGetUserById() {
		service.getUserById(USER_ID);
		verify(mockUserDao).getUserById(USER_ID);
	}
	
	@Test
	public void shouldReturnUserReturnedByDao_whenCallGetUserById() {
		User actualUser = service.getUserById(USER_ID);
		assertThat(actualUser).isEqualTo(user);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIdIsNullAndCallGetUserById() {
		service.getUserById(null);
	}
	
	@Test
	public void shouldCallGetAllUsersOnDao_whenCallGetAllUsers() {
		service.getAllUsers();
		verify(mockUserDao).getAllUsers();
	}
	
	@Test
	public void shouldReturnUsersReturnedByDao_whenCallGetAllUsers() {
		List<User> actualUserList = service.getAllUsers();
		assertThat(actualUserList).isEqualTo(users);
	}
	
	@Test
	public void shouldCallGetUserByLoginOnDaoWithGivenUserLogin_whenCallGetUserByLogin() {
		service.getUserByLogin(USER_LOGIN);
		verify(mockUserDao).getUserByLogin(USER_LOGIN);
	}
	
	@Test
	public void shouldReturnUserReturnedByDao_whenCallGetUserByLogin() {
		User actualUser = service.getUserByLogin(USER_LOGIN);
		assertThat(actualUser).isEqualTo(user);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsNullAndCallGetUserByLogin() {
		service.getUserByLogin(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsEmptyAndCallGetUserByLogin() {
		service.getUserByLogin(EMPTY);
	}
	
	@Test
	public void shouldCallDeleteUserOnDaoWithGivenUser_whenCallDeleteUser() {
		service.deleteUser(user);
		verify(mockUserDao).deleteUser(user);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallDeleteUser() {
		service.deleteUser(null);
	}
	
	@Test
	public void shouldCallDeleteUserByIdOnDaoWithGivenUserId_whenCallDeleteUserById() {
		service.deleteUserById(USER_ID);
		verify(mockUserDao).deleteUserById(USER_ID);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIdIsNullAndCallDeleteUserById() {
		service.deleteUserById(null);
	}
	
	@Test
	public void shouldCallDeleteUserByLoginOnDaoWithGivenUserLogin_whenCallDeleteUserByLogin() {
		service.deleteUserByLogin(USER_LOGIN);
		verify(mockUserDao).deleteUserByLogin(USER_LOGIN);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsNullAndCallDeleteUserByLogin() {
		service.deleteUserByLogin(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsEmptyAndCallDeleteUserByLogin() {
		service.deleteUserByLogin(EMPTY);
	}
	
	@Test
	public void shouldCallAddUserWithGivenUser_whenCallAddUser() {
		service.addUser(user);
		verify(mockUserDao).addUser(user);
	}
	
	@Test
	public void shouldReturnUserReturnedByDao_whenCallAddUser() {
		User actualUser = service.addUser(user);
		assertThat(actualUser).isEqualTo(user);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallAddUser() {
		service.addUser(null);
	}
	
	@Test
	public void shouldCallUpdateUserWithGivenUser_whenCallUpdateUser() {
		service.updateUser(user);
		verify(mockUserDao).updateUser(user);
	}
	
	@Test
	public void shouldReturnUserReturnedByDao_whenCallUpdateUser() {
		User actualUser = service.updateUser(user);
		assertThat(actualUser).isEqualTo(user);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallUpdateUser() {
		service.updateUser(null);
	}
	
	@Test
	public void shouldReturnCurrentUser_whenGetCurrentUser(){
		User actualUser = service.getCurrentUser();
		assertThat(actualUser).isSameAs(mockCurrentUser);
	}
}