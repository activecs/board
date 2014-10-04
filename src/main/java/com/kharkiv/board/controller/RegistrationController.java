package com.kharkiv.board.controller;

import static java.io.File.separator;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.google.gson.Gson;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.service.RegistrationService;

@MultipartConfig
@WebServlet("/registration")
public class RegistrationController extends AbstractAutowiringServlet {

    private static final String ERROR_MESSAGE_LARGE_IMAGE_SIZE = "sign.up.image.too.big";
    private static final String ERROR_MESSAGE_USER_ALREADY_EXIST = "sign.up.existent.user";
	private static final String ERROR_MESSAGE_PASSWORDS_NOT_MATCH = "sing.up.pass.conf.not.match";
	private static final long serialVersionUID = 1L;
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String PASSWORD_CONFIRMATION_PARAMETER = "confirm_password";
    private static final String FILEUPLOAD_PARAMETER = "files";
    private static final String AVATAR_FILENAME = "filename";
    private static final String CONTENT_DISPOSITION_HEADER = "content-disposition";
    private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L; // 5BM

    private String avatarStorage = EMPTY;
    @Value(value = "${avatar.storage.folder}")
    private String avatarDir;
    @Value(value = "${avatar.default}")
    private String defaultAvatar;
    @Inject
    private RegistrationService registrationService;
    @Inject
    private Validator validator;
    @Inject
    private ReloadableResourceBundleMessageSource messageSource;

    Gson gson = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initAvatarDir(config);
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationResponse response = new RegistrationResponse();

        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String passwordConf = req.getParameter(PASSWORD_CONFIRMATION_PARAMETER);
        Locale locale = req.getLocale();

        List<Error> errors = new ArrayList<>();
        errors.add(this.validatePasswordConfirmation(password, passwordConf, locale));

        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);

        errors.addAll(this.validateUserConstraints(newUser, locale));
        errors.add(this.validaUserExistence4Login(login, locale));

        Part imagePart = getAvatarPart(req.getParts());
        errors.add(this.saveAvatar(imagePart, newUser, locale));

        errors.removeAll(Collections.singleton(null));
        if (CollectionUtils.isNotEmpty(errors)) {
            response.setValid(false);
            response.setErrors(errors);
        } else {
            response.setValid(true);
        }

        if (response.isValid())
            registrationService.createNewUser(newUser);

        this.sendJsonResponse(response, resp);
    }

    private void sendJsonResponse(RegistrationResponse response, HttpServletResponse resp) throws IOException {
        String jsonResp = gson.toJson(response);
        PrintWriter out = resp.getWriter();
        out.write(jsonResp);
        out.flush();
        out.close();
    }
    
    private Error validatePasswordConfirmation(String pass, String passConf, Locale loc) {
        if (!StringUtils.equals(pass, passConf)) {
            return new Error(PASSWORD_CONFIRMATION_PARAMETER, messageSource.getMessage(
                    ERROR_MESSAGE_PASSWORDS_NOT_MATCH, null, loc));
        }
        return null;
    }
    
    private List<Error> validateUserConstraints(User user, Locale loc) {
        List<Error> errors = new ArrayList<>();
        Set<ConstraintViolation<User>> validationResult = validator.validate(user);
        if (isNotEmpty(validationResult)) {
            Iterator<ConstraintViolation<User>> resIterator = validationResult.iterator();
            while (resIterator.hasNext()) {
                String[] errParts = resIterator.next().getMessage().split(":");
                String errorMessage = messageSource.getMessage(errParts[1], null, loc);
                Error err = new Error(errParts[0], errorMessage);
                errors.add(err);
            }
        }
        return errors;
    }
    
    private Error validaUserExistence4Login(String login, Locale loc) {
        if (isNotBlank(login) && registrationService.isExistentUser(login)) {
            return new Error(LOGIN_PARAMETER, messageSource.getMessage(ERROR_MESSAGE_USER_ALREADY_EXIST, null, loc));
        }
        return null;
    }
    
    private Error saveAvatar(Part image, User user, Locale loc) throws IOException {
        if (image != null) {
            Error err = this.validateAvatarSize(image, loc);
            if (err == null) {
                String fileName = extractFileName(image);
                image.write(avatarStorage + fileName);
                user.setLogo(fileName);
            } else {
                return err;
            }
        } else {
            user.setLogo(defaultAvatar);
        }
        return null;
    }
    
    private Error validateAvatarSize(Part image, Locale loc) { 
        if (image.getSize() > MAX_FILE_SIZE) {
            return new Error(FILEUPLOAD_PARAMETER,
                    messageSource.getMessage(ERROR_MESSAGE_LARGE_IMAGE_SIZE, null, loc));
        }
        return null;
    }
    
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader(CONTENT_DISPOSITION_HEADER);
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith(AVATAR_FILENAME)) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return StringUtils.EMPTY;
    }
    
    private void initAvatarDir(ServletConfig config) {
		String base = config.getServletContext().getRealPath(StringUtils.EMPTY);
        avatarStorage =  base + separator + avatarDir + separator;
        File fileSaveDir = new File(avatarStorage);
        if (!fileSaveDir.exists())
            fileSaveDir.mkdir();
	}
    
    private Part getAvatarPart(Collection<Part> parts) {
        String fileName = StringUtils.EMPTY;
        for (Part part : parts) {
            fileName = extractFileName(part);
            if (isNotBlank(fileName)) {
                return part;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    private static class RegistrationResponse {

        private boolean isValid;
        private List<Error> errors;

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean isValid) {
            this.isValid = isValid;
        }

        public void setErrors(List<Error> errs) {
            errors = errs;
        }
    }
    
    @SuppressWarnings("unused")
    private static class Error {
        private String field;
        private String errMsg;
        
        public Error(String field, String errMsg) {
            this.field = field;
            this.errMsg = errMsg;
        }
    }
}
