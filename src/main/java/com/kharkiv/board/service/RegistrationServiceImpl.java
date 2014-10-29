package com.kharkiv.board.service;

import static com.google.common.collect.Sets.newHashSet;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.dto.user.Role;
import com.kharkiv.board.dto.user.User;

@Transactional
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

	@Inject
	private UserService userService;
	@Inject
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public boolean isExistentUser(String username) {
		User user = userService.getUserByUsername(username);
		return user != null;
	}

	@Override
	public void createNewUser(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		user.setRoles(newHashSet(new Role()));
		userService.addUser(user);
	}

}