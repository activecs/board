package com.kharkiv.board.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
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
    public User getUserByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.GET_BY_USERNAME, User.class);
        List<User> users = query.setParameter("username", username).getResultList();
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
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
    public int deleteUserByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.DELETE_BY_USERNAME, User.class);
        return query.setParameter("username", username).executeUpdate();
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
