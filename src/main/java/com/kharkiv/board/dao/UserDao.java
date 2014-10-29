package com.kharkiv.board.dao;

import java.util.List;

import com.kharkiv.board.dto.user.User;

public interface UserDao {

	List<User> getAllUsers();

	User getUserById(Integer id);

	User getUserByUsername(String username);

	void deleteUser(User user);

	int deleteUserById(Integer id);

	int deleteUserByUsername(String username);

	User addUser(User user);

	User updateUser(User user);
}
