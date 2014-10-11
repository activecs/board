package com.kharkiv.board.service;

import static java.io.File.separator;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.dto.user.UserRole;

@Transactional
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {
	
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
    @Inject
    private UserService userService;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject 
	private ServletContext servletContext;
    @Value("${avatar.storage.folder}")
    private String avatarDir;
    @Value("${avatar.default}")
    private String defaultAvatar;
    private String avatarStorage = EMPTY;
    
    @PostConstruct
    public void initAvatarDir() {
		String base = servletContext.getRealPath(EMPTY);
        avatarStorage =  base + separator + avatarDir + separator;
        File fileSaveDir = new File(avatarStorage);
        if (!fileSaveDir.exists())
            fileSaveDir.mkdir();
	}
    
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

	@Override
	public User saveUserAvatar(User user, MultipartFile file) throws IOException {
		if (file.isEmpty())
			user.setLogo(defaultAvatar);
		else {
			String filename = file.getOriginalFilename();
			try(BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(avatarStorage + filename)))){
				buffStream.write(file.getBytes());
				buffStream.close();
				user.setLogo(filename);
			}catch(IOException e){
				LOG.error("Cannot save user avatar", e);
				throw e;
			}
		}
		return user;
	}
}