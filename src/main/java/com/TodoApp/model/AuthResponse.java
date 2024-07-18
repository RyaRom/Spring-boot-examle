package com.TodoApp.model;

public class AuthResponse {
    private String jwt;

    public AuthResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}
