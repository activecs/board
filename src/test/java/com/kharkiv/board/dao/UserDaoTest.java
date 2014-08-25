package com.kharkiv.board.dao;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.UserQueries;

public class UserDaoTest {

    private static final Integer ID = 1;
    private static final String LOGIN = "login";

    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<User> query;

    private User user;

    @InjectMocks
    private UserDao userDao = new UserDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setId(ID);
        user.setLogin(LOGIN);

        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    public void shouldReturnAllUsers_whenCallGetAllUsers() {
        when(query.getResultList()).thenReturn(Arrays.<User> asList(user));
        List<User> allUsers = userDao.getAllUsers();
        assertThat(allUsers).containsOnly(user);
        verify(em).createNamedQuery(UserQueries.GET_ALL, User.class);
        verify(query).getResultList();
    }

    @Test
    public void shouldSetGivenidInQuery_whenCallGetUserById() {

        when(query.getSingleResult()).thenReturn(user);
        User userById = userDao.getUserById(ID);
        verify(em).createNamedQuery(UserQueries.GET_BY_ID, User.class);
        verify(query).setParameter("id", ID);
        verify(query).getSingleResult();
        assertEquals(user, userById);
    }

    @Test
    public void shouldSetGivenLoginInQuery_whenCallGetUserByLogin() {
        when(query.getSingleResult()).thenReturn(user);
        User userByLogin = userDao.getUserByLogin(LOGIN);
        verify(em).createNamedQuery(UserQueries.GET_BY_LOGIN, User.class);
        verify(query).setParameter("login", LOGIN);
        verify(query).getSingleResult();
        assertNotNull(userByLogin);
        assertEquals(LOGIN, userByLogin.getLogin());
    }

    @Test
    public void verifyUser_whenCallDeleteUser() {
        User user = new User();
        userDao.deleteUser(user);
        verify(em).remove(user);
    }

    @Test
    public void shouldReturnCountOfDeletedRows_whenCallDeleteUserById() {
        when(query.executeUpdate()).thenReturn(1);
        int deleted = userDao.deleteUserById(ID);
        verify(em).createNamedQuery(UserQueries.DELETE_BY_ID, User.class);
        verify(query).setParameter("id", ID);
        assertEquals(1, deleted);
    }

    @Test
    public void shouldReturnCountOfDeletedRows_whenCallDeleteUserByLogin() {
        when(query.executeUpdate()).thenReturn(1);
        int deleted = userDao.deleteUserByLogin(LOGIN);
        verify(em).createNamedQuery(UserQueries.DELETE_BY_LOGIN, User.class);
        verify(query).setParameter("login", LOGIN);
        assertEquals(1, deleted);
    }

    @Test
    public void shouldPersistGiveUser_whenCallAddUser() {
        User added = userDao.addUser(user);
        verify(em).persist(user);
        verify(em).flush();
        assertSame(user, added);
    }

    @Test
    public void shouldFlushedPersistedUser_whenCallAddUser() {
        User added = userDao.addUser(user);
        verify(em).flush();
        assertSame(user, added);
    }

    @Test
    public void shouldUpdateGivenUser_whenCallUpdateUser() {
        when(em.merge(any(User.class))).thenReturn(user);
        User updated = userDao.updateUser(user);
        verify(em).merge(user);
        assertSame(user, updated);
    }
}
