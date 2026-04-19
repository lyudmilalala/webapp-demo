package com.jerry.webappdemojpa.user.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String name;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
