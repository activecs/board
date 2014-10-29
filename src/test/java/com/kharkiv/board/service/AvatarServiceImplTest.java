package com.kharkiv.board.service;

import static java.lang.System.getProperty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.kharkiv.board.dto.user.User;

@RunWith(MockitoJUnitRunner.class)
public class AvatarServiceImplTest {

	private static final String DEFAULT_AVATAR_FILENAME = "defaultAvatar";
	private static final String DEFAULT_AVATAR = DEFAULT_AVATAR_FILENAME;
	private static final String AVATAR_DIRECTORY = "avatarDirectory";
	private static final String AVATAR_DIR_FIELD_NAME = "avatarDir";
	private static final byte[] FILE_CONTENT = new byte[] { 1 };
	private static final String MULTIPART_FORM_DATA = "multipart/form-data";
	private static final String OS_IO_TMPDIR = "java.io.tmpdir";
	private static final String AVATAR_FILENAME = "logo";
	private static final String AVATAR_FILENAME_EXTENSION = ".png";
	private static final String AVATAR_FILENAME_HASHED = "c8f7fe3b0e41be846d5687592cf2018ff6e22687";

	@InjectMocks
	private AvatarServiceImpl avatarService = new AvatarServiceImpl();
	@Mock
	private ServletContext mockServletContext;
	@Mock
	private MultipartFile mockEmptyFile;

	private User user = new User();
	private MockMultipartFile mockFile = new MockMultipartFile(AVATAR_FILENAME,
			AVATAR_FILENAME + AVATAR_FILENAME_EXTENSION, MULTIPART_FORM_DATA, FILE_CONTENT);

	@Before
	public void setUp() {
		when(mockServletContext.getRealPath(EMPTY)).thenReturn(getProperty(OS_IO_TMPDIR));
		injectField(DEFAULT_AVATAR_FILENAME, DEFAULT_AVATAR);
		injectField(AVATAR_DIR_FIELD_NAME, AVATAR_DIRECTORY);
	}

	@Test
	public void shoulbSaveAvatarWithHashedName_whenMultipartFileIsNotEmpty() throws Exception {
		avatarService.initAvatarDir();
		String expectedAvatarName = AVATAR_FILENAME_HASHED + AVATAR_FILENAME_EXTENSION;
		avatarService.saveUserAvatar(user, mockFile);
		assertThat(user.getLogo()).isEqualTo(expectedAvatarName);
	}

	@Test
	public void shoulbSaveDefaultAvatar_whenMultipartFileIsEmpty() throws Exception {
		when(mockEmptyFile.isEmpty()).thenReturn(true);
		avatarService.saveUserAvatar(user, mockEmptyFile);
		assertThat(user.getLogo()).isEqualTo(DEFAULT_AVATAR);
	}
	
	private void injectField(String fieldName, Object value) {
		Field field = findField(avatarService.getClass(), fieldName);
		makeAccessible(field);
		setField(field, avatarService, value);
	}
}
