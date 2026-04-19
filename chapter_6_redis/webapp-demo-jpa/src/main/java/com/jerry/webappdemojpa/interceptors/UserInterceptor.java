package com.jerry.webappdemojpa.interceptors;

import com.jerry.webappdemojpa.user.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        log.info("request uri = "+request.getRequestURI());
        String token = request.getHeader("token");
        if(token != null){
            Claims c = JwtUtils.getMemberJwtClaims(token);
            if (c != null) {
  				log.info("Pass user interceptor.");
                return true;
            } else {
                log.warn("Token info is not correct.");
                response.sendError(401, "Broken user token.");
                return false;
            }
        } else {
            log.error("request does not have token");
            response.sendError(401, "Request does not have token.");
            return false;
        }
    }
}
