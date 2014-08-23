package com.kharkiv.board.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.UserQueries;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.GET_ALL, User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(Integer id) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.GET_BY_ID, User.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public User getUserByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.GET_BY_LOGIN, User.class);
        return query.setParameter("login", login).getSingleResult();
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    public int deleteUserById(Integer id) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.DELETE_BY_ID, User.class);
        return query.setParameter("id", id).executeUpdate();
    }

    @Override
    public int deleteUserByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.DELETE_BY_LOGIN, User.class);
        return query.setParameter("login", login).executeUpdate();
    }

    @Override
    public User addUser(User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    @Override
    public User updateUser(User user) {
        return em.merge(user);
    }

}
