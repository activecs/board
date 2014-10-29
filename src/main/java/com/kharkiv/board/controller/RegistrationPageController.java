package com.kharkiv.board.controller;

import static java.util.Collections.singleton;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.service.AvatarService;
import com.kharkiv.board.service.RegistrationService;

@RestController
@RequestMapping("/registration")
public class RegistrationPageController {

	private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L; // 5MB 
	private static final String USERNAME_PARAMETER = "login";
	private static final String PASSWORD_CONFIRMATION_PARAMETER = "confirm_password";
	private static final String AVATAR_PARAMETER = "avatar-preview";
	private static final String ERROR_MESSAGE_LARGE_IMAGE_SIZE = "sign.up.image.too.big";
	private static final String ERROR_MESSAGE_USER_ALREADY_EXIST = "sign.up.existent.user";
	private static final String ERROR_MESSAGE_PASSWORDS_NOT_MATCH = "sing.up.pass.conf.not.match";

	@Inject
	private RegistrationService registrationService;
	@Inject
	private AvatarService avatarService;
	@Inject
	private Validator validator;
	@Inject
	private ReloadableResourceBundleMessageSource messageSource;

	@RequestMapping(method = RequestMethod.POST)
	public RegistrationResponse register(
			@RequestParam String login,
			@RequestParam String password,
			@RequestParam(PASSWORD_CONFIRMATION_PARAMETER) String confirmPassword,
			@RequestParam MultipartFile file) throws IOException, NoSuchAlgorithmException {
		User newUser = new User();
		newUser.setUsername(login);
		newUser.setPassword(password);

		List<Error> errors = validate(confirmPassword, file, newUser);

		RegistrationResponse response = new RegistrationResponse();
		if (isNotEmpty(errors)) {
			response.isValid = false;
			response.errors = errors;
		} else {
			response.isValid = true;
			registrationService.createNewUser(newUser);
		}
		return response;
	}

	private List<Error> validate(String confirmPassword, MultipartFile file, User newUser) throws IOException, NoSuchAlgorithmException {
		List<Error> errors = Lists.newArrayList();
		errors.add(validatePasswordConfirmation(newUser.getPassword(), confirmPassword));
		errors.addAll(validateUserConstraints(newUser));
		errors.add(validaUserExistence4Username(newUser.getUsername()));
		errors.add(saveAvatar(file, newUser));
		errors.removeAll(singleton(null));

		return errors;
	}

	protected String getErrorMessage(String errorCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(errorCode, null, locale);
	}

	private Error validatePasswordConfirmation(String pass, String passConf) {
		if (!StringUtils.equals(pass, passConf)) {
			String message = getErrorMessage(ERROR_MESSAGE_PASSWORDS_NOT_MATCH);
			return new Error(PASSWORD_CONFIRMATION_PARAMETER, message);
		}
		return null;
	}

	private List<Error> validateUserConstraints(User user) {
		List<Error> errors = new ArrayList<>();
		Set<ConstraintViolation<User>> validationResult = validator.validate(user);
		if (isNotEmpty(validationResult)) {
			Iterator<ConstraintViolation<User>> resIterator = validationResult.iterator();
			while (resIterator.hasNext()) {
				String[] errParts = resIterator.next().getMessage().split(":");
				String errorMessage = getErrorMessage(errParts[1]);
				Error err = new Error(errParts[0], errorMessage);
				errors.add(err);
			}
		}
		return errors;
	}

	private Error validaUserExistence4Username(String username) {
		if (isNotBlank(username) && registrationService.isExistentUser(username)) {
			String message = getErrorMessage(ERROR_MESSAGE_USER_ALREADY_EXIST);
			return new Error(USERNAME_PARAMETER, message);
		}
		return null;
	}

	private Error saveAvatar(MultipartFile file, User user) throws IOException, NoSuchAlgorithmException {
		Error validationError = validateAvatarSize(file);
		if (validationError != null)
			return validationError;
		avatarService.saveUserAvatar(user, file);
		return null;
	}

	private Error validateAvatarSize(MultipartFile image) {
		if (image.getSize() > MAX_FILE_SIZE) {
			String message = getErrorMessage(ERROR_MESSAGE_LARGE_IMAGE_SIZE);
			return new Error(AVATAR_PARAMETER, message);
		}
		return null;
	}

	private class RegistrationResponse {
		public boolean isValid;
		public List<Error> errors;
	}

	private class Error {
		public final String field;
		public final String errMsg;

		public Error(String field, String errMsg) {
			this.field = field;
			this.errMsg = errMsg;
		}
	}
}