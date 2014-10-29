package com.kharkiv.board.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kharkiv.board.dto.user.User;

public class RegistrationServiceTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encoded_password";
    
    private User user = new User();
    
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private RegistrationService registrationService = new RegistrationServiceImpl();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user.setPassword(PASSWORD);
    }
    
    @Test
    public void shouldReturnTrue_whenUserIsExists() {
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        assertTrue(registrationService.isExistentUser(LOGIN));
        verify(userService).getUserByUsername(LOGIN);
    }
    
    @Test
    public void shouldReturnFalse_whenUserIsNotExists() {
        when(userService.getUserByUsername(anyString())).thenReturn(null);
        assertFalse(registrationService.isExistentUser(LOGIN));
    }
    
    @Test
    public void shouldEncodePasswordAndSetUserRole_whenCall() {
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        registrationService.createNewUser(user);
        verify(passwordEncoder).encode(PASSWORD);
        user.setPassword(ENCODED_PASSWORD);
        verify(userService).addUser(user);
    }
    
    
}
