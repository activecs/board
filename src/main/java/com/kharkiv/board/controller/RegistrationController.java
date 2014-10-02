package com.kharkiv.board.controller;

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

import org.apache.commons.collections.CollectionUtils;
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


    private static final long serialVersionUID = 1L;

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String PASSWORD_CONFIRMATION_PARAMETER = "confirm_password";
    private static final String FILEUPLOAD_PARAMETER = "fileupload";
    private static final String AVATAR_FILENAME = "filename";
    private static final String CONTENT_DISPOSITION_HEADER = "content-disposition";

    private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L; // 5BM

    @Value(value = "${avatar.storage.folder}")
    private String avatarDir;
    @Value(value = "${avatar.default}")
    private String defaultAvatar;

    private String avatarStorage = StringUtils.EMPTY;

    @Inject
    private RegistrationService registrationService;
    @Inject
    private Validator validator;
    @Inject
    private ReloadableResourceBundleMessageSource messageSource;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        avatarStorage = config.getServletContext().getRealPath(StringUtils.EMPTY) + File.separator + avatarDir
                + File.separator;
        File fileSaveDir = new File(avatarStorage);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter(LOGIN_PARAMETER);
        String pass = req.getParameter(PASSWORD_PARAMETER);
        String passConf = req.getParameter(PASSWORD_CONFIRMATION_PARAMETER);

        RegistrationResponse response = new RegistrationResponse();

        if (!StringUtils.equals(pass, passConf)) {
            response.addError(PASSWORD_CONFIRMATION_PARAMETER,
                    messageSource.getMessage("sing.up.pass.conf.not.match", null, req.getLocale()));
        }

        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(pass);

        Set<ConstraintViolation<User>> validationResult = validator.validate(newUser);
        if (CollectionUtils.isNotEmpty(validationResult)) {
            Iterator<ConstraintViolation<User>> resIterator = validationResult.iterator();
            while (resIterator.hasNext()) {
                String[] errParts = resIterator.next().getMessage().split(":");
                String errMsg = messageSource.getMessage(errParts[1], null, req.getLocale());
                response.addError(errParts[0], errMsg);
            }
        }

        if (StringUtils.isNotBlank(login) && registrationService.isExistentUser(login)) {
            response.addError(LOGIN_PARAMETER, messageSource.getMessage("sign.up.existent.user", null, req.getLocale()));
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

        if (response.isValid()) {
            registrationService.createNewUser(newUser);
        }
        
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

    private Part getAvatarPart(Collection<Part> parts) {
        String fileName = StringUtils.EMPTY;
        for (Part part : parts) {
            fileName = extractFileName(part);
            if (StringUtils.isNotBlank(fileName)) {
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
