package com.kharkiv.board.controller;

import static java.io.File.separator;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.google.gson.Gson;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.service.RegistrationService;

@MultipartConfig
@WebServlet("/registration")
public class RegistrationController extends AbstractAutowiringServlet {

    private static final String ERROR_MESSAGE_USER_ALREADY_EXIST = "sign.up.existent.user";
	private static final String ERROR_MESSAGE_PASSWORDS_NOT_MATCH = "sing.up.pass.conf.not.match";
	private static final long serialVersionUID = 1L;
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String PASSWORD_CONFIRMATION_PARAMETER = "confirm_password";
    private static final String FILEUPLOAD_PARAMETER = "fileupload";
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initAvatarDir(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	RegistrationResponse response = new RegistrationResponse();
        
    	String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String passwordConf = req.getParameter(PASSWORD_CONFIRMATION_PARAMETER);


        if (!StringUtils.equals(password, passwordConf)) {
            response.addError(PASSWORD_CONFIRMATION_PARAMETER,
                    messageSource.getMessage(ERROR_MESSAGE_PASSWORDS_NOT_MATCH, null, req.getLocale()));
        }

        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);

        Set<ConstraintViolation<User>> validationResult = validator.validate(newUser);
        if (isNotEmpty(validationResult)) {
            Iterator<ConstraintViolation<User>> resIterator = validationResult.iterator();
            while (resIterator.hasNext()) {
                String[] errParts = resIterator.next().getMessage().split(":");
                String errorMessage = messageSource.getMessage(errParts[1], null, req.getLocale());
                response.addError(errParts[0], errorMessage);
            }
        }

        if (isNotBlank(login) && registrationService.isExistentUser(login)) {
            response.addError(LOGIN_PARAMETER, messageSource.getMessage(ERROR_MESSAGE_USER_ALREADY_EXIST, null, req.getLocale()));
        }
        
        Part imagePart = getAvatarPart(req.getParts());
        if (imagePart != null) {
            if (imagePart.getSize() > MAX_FILE_SIZE) {
                response.addError(FILEUPLOAD_PARAMETER,
                        messageSource.getMessage("sign.up.image.too.big", null, req.getLocale()));
            } else {
                String fileName = extractFileName(imagePart);
                imagePart.write(avatarStorage + fileName);
                newUser.setLogo(fileName);
            }
        } else {
            newUser.setLogo(defaultAvatar);
        }

        response.setValid(MapUtils.isEmpty(response.getErrors()));

        if (response.isValid())
            registrationService.createNewUser(newUser);
        
        resp.setCharacterEncoding(CharEncoding.UTF_8);

        Gson gson = new Gson();
        String jsonResp = gson.toJson(response);
        PrintWriter out = resp.getWriter();
        out.write(jsonResp);
        out.flush();
        out.close();
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

    private static class RegistrationResponse {

        private boolean isValid;
        private Map<String, String> errors;

        public RegistrationResponse() {
            errors = new HashMap<>();
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean isValid) {
            this.isValid = isValid;
        }

        public Map<String, String> getErrors() {
            return errors;
        }

        public void addError(String field, String errorMsgs) {
            errors.put(field, errorMsgs);
        }
    }
}
