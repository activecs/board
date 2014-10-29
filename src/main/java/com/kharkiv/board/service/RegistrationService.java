package com.kharkiv.board.service;

import com.kharkiv.board.dto.user.User;

public interface RegistrationService {

	boolean isExistentUser(String username);

	void createNewUser(User user);
}
