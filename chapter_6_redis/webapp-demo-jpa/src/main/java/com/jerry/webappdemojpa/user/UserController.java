package com.jerry.webappdemojpa.user;

import com.jerry.webappdemojpa.CommonResponse;
import com.jerry.webappdemojpa.user.dto.LoginRequest;
import com.jerry.webappdemojpa.user.dto.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Controller
@RequestMapping(value="/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest userAccountLogInRequest) {
        LoginResponse loginResponse = userService.handleLogin(userAccountLogInRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.valueOf(loginResponse.getStatus()));
    }

    @RequestMapping(value="/getUserInfo", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> getUserInfo(@RequestHeader("token") String token) {
        CommonResponse commonResponse = userService.handleGetUserInfo(token);
        return new ResponseEntity<>(commonResponse, HttpStatus.valueOf(commonResponse.getStatus()));
    }

}
