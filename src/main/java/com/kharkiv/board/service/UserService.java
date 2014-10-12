package com.kharkiv.board.service;

import java.util.List;

import com.kharkiv.board.dto.user.User;

public interface UserService {

	List<User> getAllUsers();

	User getUserById(Integer id);

	User getUserByLogin(String login);

	void deleteUser(User user);

	void deleteUserById(Integer id);

	void deleteUserByLogin(String login);

	User addUser(User user);

	User updateUser(User user);

	User getCurrentUser();

}
