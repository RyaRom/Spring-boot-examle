package com.TodoApp.controller;

import com.TodoApp.utils.JWTUtil;
import com.TodoApp.model.AuthRequest;
import com.TodoApp.model.AuthResponse;
import com.TodoApp.model.User;
import com.TodoApp.model.UserAuthDetails;
import com.TodoApp.service.UserAuthDetailsService;
import com.TodoApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final BCryptPasswordEncoder getPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserAuthDetailsService userAuthDetailsService;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserAuthDetailsService userAuthDetailsService, JWTUtil jwtUtil, UserService userService, BCryptPasswordEncoder getPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userAuthDetailsService = userAuthDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.getPasswordEncoder = getPasswordEncoder;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthRequest authRequest){
        try {
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        final  UserAuthDetails userAuthDetails = userAuthDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.createToken(userAuthDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
    @PostMapping("/register")
    public ResponseEntity<User> register (@RequestBody User user){
        user.setPassword(getPasswordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return ResponseEntity.ok(userService.addUser(user));
    }
}
