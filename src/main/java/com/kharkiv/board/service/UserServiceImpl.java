package com.kharkiv.board.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;

import javax.inject.Inject;

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
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
    }

	@Override
	@Transactional(readOnly = true)
	public User getUserById(Integer id) {
		if(id == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_ID_CANNOT_BE_NULL);
		return userDao.getUserById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByLogin(String login) {
		if(isEmpty(login))
			throw new IllegalArgumentException(ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY );
		return userDao.getUserByLogin(login);
	}

	@Override
	public void deleteUser(User user) {
		if(user == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_CANNOT_BE_NULL);
		userDao.deleteUser(user);
	}

	@Override
	public void deleteUserById(Integer id) {
		if(id == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_ID_CANNOT_BE_NULL);
		userDao.deleteUserById(id);
	}

	@Override
	public void deleteUserByLogin(String login) {
		if(isEmpty(login))
			throw new IllegalArgumentException(ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY );
		userDao.deleteUserByLogin(login);
	}

	@Override
	public User addUser(User user) {
		if(user == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_CANNOT_BE_NULL);
		return userDao.addUser(user);
	}

	@Override
	public User updateUser(User user) {
		if(user == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_CANNOT_BE_NULL);
		return userDao.updateUser(user);
	}

}
