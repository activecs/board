package com.kharkiv.board.service;

import com.kharkiv.board.dto.user.User;

public interface RegistrationService {

    boolean isExistentUser(String login);
    
    void createNewUser(User user);
}
