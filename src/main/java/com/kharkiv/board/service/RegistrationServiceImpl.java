package com.kharkiv.board.service;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.dto.user.UserRole;

@Transactional
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

	@Inject
	private UserService userService;
	@Inject
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public boolean isExistentUser(String login) {
		User user = userService.getUserByLogin(login);
		return user != null;
	}

	@Override
	public void createNewUser(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		user.setRole(UserRole.USER);
		userService.addUser(user);
	}

}