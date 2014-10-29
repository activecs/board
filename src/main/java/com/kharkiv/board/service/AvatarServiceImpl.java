package com.kharkiv.board.service;

import static java.io.File.separator;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.kharkiv.board.dto.user.User;

public class AvatarServiceImpl implements AvatarService {  

	private static final Logger LOG = LoggerFactory.getLogger(AvatarServiceImpl.class);
	private static final String SHA_1 = "SHA-1";
	
	@Inject
	private ServletContext servletContext;
	@Value("${avatar.default}")
	private String defaultAvatar;
	@Value("${avatar.storage.folder}") 
	private String avatarDir;
	private String avatarStorage = EMPTY;

	@PostConstruct
	public void initAvatarDir() {
		String base = servletContext.getRealPath(EMPTY);
		avatarStorage = base + separator + avatarDir + separator;
		File fileSaveDir = new File(avatarStorage);
		if (!fileSaveDir.exists())
			fileSaveDir.mkdir();
	}

	@Override
	public void saveUserAvatar(User user, MultipartFile file)
			throws IOException, NoSuchAlgorithmException {
		if (file.isEmpty())
			user.setLogo(defaultAvatar);
		else {
			
			String avatarImgExtension = parsImgExtension(file.getOriginalFilename());			
			String hashNameOfAvatar = getHashNameOfAvatar(user);
			String fullHashNameOfAvatar = hashNameOfAvatar + avatarImgExtension;
			
			try (BufferedOutputStream buffStream = new BufferedOutputStream(
					new FileOutputStream(new File(avatarStorage + fullHashNameOfAvatar)))) {
				buffStream.write(file.getBytes());
				buffStream.close();
				user.setLogo(fullHashNameOfAvatar);
			} catch (IOException e) {
				LOG.error("Cannot save user avatar", e);
				throw e;
			}
		}
	}

	private String getHashNameOfAvatar(User user)
			throws NoSuchAlgorithmException {
		String newNameOfAvatar = user.getUsername() + user.getId();

		MessageDigest md = MessageDigest.getInstance(SHA_1);
		md.update(newNameOfAvatar.getBytes());

		byte byteData[] = md.digest();

		StringBuilder hashNameOfAvatar = new StringBuilder();
		for (int i = 0; i < byteData.length; i++)
			hashNameOfAvatar.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		
		return hashNameOfAvatar.toString();
	}

	private String parsImgExtension(String str) {
		return str.substring(str.lastIndexOf('.'), str.length());
	}
}
