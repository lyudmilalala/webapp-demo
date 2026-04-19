package com.jerry.webappdemojpa.user.dto;

import com.jerry.webappdemojpa.CommonResponse;
import lombok.Data;

@Data
public class LoginResponse extends CommonResponse {
    private String token;
    private String name;
    private boolean hasPassword;

    public LoginResponse() {
        super();
    }

    public LoginResponse(String token, String name, boolean hasPassword) {
        super();
        this.token = token;
        this.name = name;
        this.hasPassword = hasPassword;
    }

    public LoginResponse(int status, String msg) {
        super(status, msg);
    }

}
