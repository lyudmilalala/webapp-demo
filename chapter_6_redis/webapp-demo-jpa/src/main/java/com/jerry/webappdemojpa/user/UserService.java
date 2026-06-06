package com.jerry.webappdemojpa.user;

import com.jerry.webappdemojpa.CommonEntityResponse;
import com.jerry.webappdemojpa.user.dto.LoginRequest;
import com.jerry.webappdemojpa.user.dto.LoginResponse;
import com.jerry.webappdemojpa.user.dto.UserInfoDTO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponse handleLogin(LoginRequest request) {
        log.info("============== Enter handleLogin ==============");
        LoginResponse loginResponse = null;
        try {
            List<UserEntity> userlist = userRepository.findActiveUserByLoginNameAndPassowrd(request.getName(), request.getName(), request.getPassword());
            if (userlist.size() > 1) {
                log.error("userlist with loginRequest " + request + " has more than 1 user.");
                log.info("userlist = " + userlist);
                loginResponse = new LoginResponse(HttpStatus.PRECONDITION_FAILED.value(), "Multiple users found with the same credentials.");
            } else if (userlist.isEmpty()) {
                log.error("userlist with accountLoginRequest " + request + " is empty");
                loginResponse = new LoginResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Invalid username or password. Please try again.");
            } else {
                UserEntity user = userlist.get(0);
                if (user != null) {
                    log.info("find user = {}", user);
                    user.setLatestLoginTime(new Date());
                    user = userRepository.save(user);
                    String token = JwtUtils.getJwtToken(user.getId(), user.getUname(), JwtUtils.WEB_EXPIRE);
                    loginResponse = new LoginResponse(token, user.getUname(), true);
                } else {
                    log.error("user with loginRequest " + request + " is null or deleted");
                    loginResponse = new LoginResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Invalid username or password. Please try again.");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            loginResponse = new LoginResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error. Please try again later.");
        }
        log.info("============== Exit handleLogin ==============");
        return loginResponse;
    }

    public CommonEntityResponse<UserInfoDTO> handleGetUserInfo(String token) {
        log.info("============== Enter handleGetUserInfo ==============");
        CommonEntityResponse<UserInfoDTO> commonResponse = null;
        try {
            Claims claims = JwtUtils.getMemberJwtClaims(token);
            if (claims != null && claims.get("accountid") != null) {
                long uid = Long.parseLong(claims.get("accountid").toString());
                log.info("uid in token = " + uid);
                if(uid > 0){
                    UserEntity u = userRepository.findById(uid);
                    if (u != null) {
                        UserInfoDTO userInfoDTO = new UserInfoDTO(u);
                        commonResponse = new CommonEntityResponse<>(userInfoDTO);
                    } else {
                        log.error("User with id = " + uid + "does not exist");
                        commonResponse = new CommonEntityResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "User not found");
                    }
                } else if (uid == 0) {
                    log.warn("This is a visitor, should not get here.");
                    commonResponse = new CommonEntityResponse<>(HttpStatus.PRECONDITION_FAILED.value(), "User cannot be a visitor");
                } else {
                    log.error("JWT decode failed.");
                    commonResponse = new CommonEntityResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Token decoding error");
                }
            } else {
                log.error("JWT decode failed.");
                commonResponse = new CommonEntityResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Token解码错误");
            }
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            commonResponse = new CommonEntityResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error. Please try again later.");
        }
        log.info("============== Exit handleGetUserInfo ==============");
        return commonResponse;
    }


}