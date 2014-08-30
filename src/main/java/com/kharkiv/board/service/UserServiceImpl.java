package com.kharkiv.board.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.dao.UserDao;
import com.kharkiv.board.dto.user.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final String ERR_MESSAGE_USER_ID_CANNOT_BE_NULL = "User id cannot be null";
	private static final String ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY = "User login cannot be empty";
	private static final String ERR_MESSAGE_USER_CANNOT_BE_NULL = "User cannot be null";
	
	@Inject
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "userServiceCache" }, key = "{#root.methodName}")
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
    }

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "userServiceCache" }, key = "{#root.methodName,#id}")
	public User getUserById(Integer id) {
		if(id == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_ID_CANNOT_BE_NULL);
		return userDao.getUserById(id);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = { "userServiceCache" }, key = "{#root.methodName,#login}")
	public User getUserByLogin(String login) {
		if(isEmpty(login))
			throw new IllegalArgumentException(ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY );
		return userDao.getUserByLogin(login);
	}

	@Override
	@CacheEvict(value = { "userServiceCache" }, condition = "#user != null", allEntries = true, beforeInvocation = true)
	public void deleteUser(User user) {
		if(user == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_CANNOT_BE_NULL);
		userDao.deleteUser(user);
	}

	@Override
	@CacheEvict(value = { "userServiceCache" }, condition = "#id != null", allEntries = true, beforeInvocation = true)
	public void deleteUserById(Integer id) {
		if(id == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_ID_CANNOT_BE_NULL);
		userDao.deleteUserById(id);
	}

	@Override
	@CacheEvict(value = { "userServiceCache" }, condition = "#login != null", allEntries = true, beforeInvocation = true)
	public void deleteUserByLogin(String login) {
		if(isEmpty(login))
			throw new IllegalArgumentException(ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY );
		userDao.deleteUserByLogin(login);
	}

	@Override
	@CacheEvict(value = { "userServiceCache" }, condition = "#user != null", allEntries = true, beforeInvocation = true)
	public User addUser(User user) {
		if(user == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_CANNOT_BE_NULL);
		return userDao.addUser(user);
	}

	@Override
	@CacheEvict(value = { "userServiceCache" }, condition = "#user != null", allEntries = true, beforeInvocation = true)
	public User updateUser(User user) {
		if(user == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_CANNOT_BE_NULL);
		return userDao.updateUser(user);
	}

}
