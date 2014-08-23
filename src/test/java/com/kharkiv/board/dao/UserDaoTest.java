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

    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<User> query;

    @InjectMocks
    private UserDao userDao = new UserDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    public void shouldReturnAllUsers_whenCallGetAllUsers() {
        User user = new User();
        when(query.getResultList()).thenReturn(Arrays.<User> asList(user));
        List<User> allUsers = userDao.getAllUsers();
        assertThat(allUsers).containsOnly(user);
        verify(em).createNamedQuery(UserQueries.GET_ALL, User.class);
        verify(query).getResultList();
    }

    @Test
    public void shouldSetGivenidInQuery_whenCallGetUserById() {
        int id = 1;
        User user = new User();
        user.setId(id);
        when(query.getSingleResult()).thenReturn(user);
        User userById = userDao.getUserById(id);
        verify(em).createNamedQuery(UserQueries.GET_BY_ID, User.class);
        verify(query).setParameter("id", id);
        verify(query).getSingleResult();
        assertEquals(user, userById);
    }

    @Test
    public void shouldSetGivenLoginInQuery_whenCallGetUserByLogin() {
        User user = new User();
        String login = "login";
        user.setLogin(login);
        when(query.getSingleResult()).thenReturn(user);
        User userByLogin = userDao.getUserByLogin(login);
        verify(em).createNamedQuery(UserQueries.GET_BY_LOGIN, User.class);
        verify(query).setParameter("login", login);
        verify(query).getSingleResult();
        assertNotNull(userByLogin);
        assertEquals(login, userByLogin.getLogin());
    }

    @Test
    public void verifyUser_whenCallDeleteUser() {
        User user = new User();
        userDao.deleteUser(user);
        verify(em).remove(user);
    }

    @Test
    public void shouldReturnCountOfDeletedRows_whenCallDeleteUserById() {
        Integer id = 1;
        when(query.executeUpdate()).thenReturn(1);
        int deleted = userDao.deleteUserById(id);
        verify(em).createNamedQuery(UserQueries.DELETE_BY_ID, User.class);
        verify(query).setParameter("id", id);
        assertEquals(1, deleted);
    }

    @Test
    public void shouldReturnCountOfDeletedRows_whenCallDeleteUserByLogin() {
        String login = "login";
        when(query.executeUpdate()).thenReturn(1);
        int deleted = userDao.deleteUserByLogin(login);
        verify(em).createNamedQuery(UserQueries.DELETE_BY_LOGIN, User.class);
        verify(query).setParameter("login", login);
        assertEquals(1, deleted);
    }

    @Test
    public void shouldPersistGiveUser_whenCallAddUser() {
        User toAdd = new User();
        User added = userDao.addUser(toAdd);
        verify(em).persist(toAdd);
        verify(em).flush();
        assertSame(toAdd, added);
    }

    @Test
    public void shouldUpdateGivenUser_whenCallUpdateUser() {
        User toUpdate = new User();
        when(em.merge(any(User.class))).thenReturn(toUpdate);
        User updated = userDao.updateUser(toUpdate);
        verify(em).merge(toUpdate);
        assertSame(toUpdate, updated);
    }
}
