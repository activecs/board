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
	private User mockUser;
	
	private List<User> users;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		users = Lists.newArrayList(mockUser);
		initMockBehaviour();
	}

	private void initMockBehaviour() {
		when(mockUserDao.getAllUsers()).thenReturn(users);
		when(mockUserDao.getUserById(USER_ID)).thenReturn(mockUser);
		when(mockUserDao.getUserByLogin(USER_LOGIN)).thenReturn(mockUser);
		when(mockUserDao.addUser(mockUser)).thenReturn(mockUser);
		when(mockUserDao.updateUser(mockUser)).thenReturn(mockUser);
	}

	@Test
	public void shouldCallGetUserByIdOnDaoWithGivenUserId_whenCallGetUserById() {
		service.getUserById(USER_ID);
		verify(mockUserDao).getUserById(USER_ID);
	}
	
	@Test
	public void shouldReturnUserReturnedByDao_whenCallGetUserById() {
		User actualUser = service.getUserById(USER_ID);
		assertThat(actualUser).isEqualTo(mockUser);
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
		assertThat(actualUser).isEqualTo(mockUser);
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
		service.deleteUser(mockUser);
		verify(mockUserDao).deleteUser(mockUser);
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
		service.addUser(mockUser);
		verify(mockUserDao).addUser(mockUser);
	}
	
	@Test
	public void shouldReturnUserReturnedByDao_whenCallAddUser() {
		User actualUser = service.addUser(mockUser);
		assertThat(actualUser).isEqualTo(mockUser);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallAddUser() {
		service.addUser(null);
	}
	
	@Test
	public void shouldCallUpdateUserWithGivenUser_whenCallUpdateUser() {
		service.updateUser(mockUser);
		verify(mockUserDao).updateUser(mockUser);
	}
	
	@Test
	public void shouldReturnUserReturnedByDao_whenCallUpdateUser() {
		User actualUser = service.updateUser(mockUser);
		assertThat(actualUser).isEqualTo(mockUser);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallUpdateUser() {
		service.updateUser(null);
	}
}