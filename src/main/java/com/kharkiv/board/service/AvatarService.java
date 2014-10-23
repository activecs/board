package com.kharkiv.board.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.multipart.MultipartFile;

import com.kharkiv.board.dto.user.User;

public interface AvatarService {
	void saveUserAvatar(User user, MultipartFile file) throws IOException, NoSuchAlgorithmException;
}
