package com.TodoApp.controller;

import com.TodoApp.Utils.JWTUtil;
import com.TodoApp.model.UserAuthDetails;
import com.TodoApp.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserAuthDetails userAuthDetails;
    private JWTUtil jwtUtil;
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserAuthDetails userAuthDetails, JWTUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userAuthDetails = userAuthDetails;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }
}
