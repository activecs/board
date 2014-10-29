package com.kharkiv.board.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kharkiv.board.dto.user.User;

public interface UserService extends UserDetailsService {

	List<User> getAllUsers();

	User getUserById(Integer id);

	User getUserByUsername(String username);

	void deleteUser(User user);

	void deleteUserById(Integer id);

	void deleteUserByUsername(String username);

	User addUser(User user);

	User updateUser(User user);

	User getCurrentUser();

}
