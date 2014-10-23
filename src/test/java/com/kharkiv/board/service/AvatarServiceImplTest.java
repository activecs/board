package com.kharkiv.board.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.kharkiv.board.dto.user.User;

@RunWith(MockitoJUnitRunner.class)
public class AvatarServiceImplTest {

	private static final String EXPEXTED_HASH_NAME_OF_AVATAR ="47739f5d0fb3bebc94dbebfca866990907767d37.png";
	private User user = new User();
	private MockMultipartFile file;

	@Mock
	private ServletContext servletContext;

	@Mock
	private MultipartFile fileForDefault;
	
	@Mock
	private BufferedOutputStream buffStream;

	@InjectMocks
	AvatarServiceImpl avatarServiceImpl = new AvatarServiceImpl();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user.setLogin("TestLogo");
		user.setId(25);

		file = new MockMultipartFile("Log", "Log.png", "multipart/form-data",
				"Hallo World".getBytes());
	}

	@Test
	public void shoulbBeSaveAvatar() throws NoSuchAlgorithmException,
			IOException {
		avatarServiceImpl.saveUserAvatar(user, file);
		assertEquals(EXPEXTED_HASH_NAME_OF_AVATAR, user.getLogo());
	}
	
	@Test
	public void shoulbBeSaveDefaultAvatar() throws NoSuchAlgorithmException,
			IOException {
		when(fileForDefault.isEmpty()).thenReturn(true);
		avatarServiceImpl.saveUserAvatar(user, fileForDefault);
		assertNull(user.getLogo());
	}
}
