package com.kharkiv.board.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.kharkiv.board.dto.user.User;

public interface RegistrationService {

	boolean isExistentUser(String login);

	void createNewUser(User user);

	User saveUserAvatar(User user, MultipartFile file) throws IOException;
}
