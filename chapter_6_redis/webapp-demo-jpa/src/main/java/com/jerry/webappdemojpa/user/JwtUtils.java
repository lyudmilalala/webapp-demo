package com.jerry.webappdemojpa.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    public static long WEB_EXPIRE;
    @Value("${jwt.period.web}")
    public void setWebExpire(long periodInSecond) {
        WEB_EXPIRE = 1000 * periodInSecond; // fit the milliseconds
    }

    private static final String USER_APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";
    private static final String ISSUER = "myApp";
    public static final String ACCOUNT_TYPE_USER = "user";

    public static String getJwtToken(long accountid, String accountname, long period) {
        log.debug("============== Enter getJwtToken ==============");
        String JwtToken = Jwts.builder().setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("test-jwt")
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date( System.currentTimeMillis() + period))
                .claim("accountid", accountid)
                .claim("accountname", accountname)
                .signWith(SignatureAlgorithm.HS256, USER_APP_SECRET).compact();
        log.info("========= Token created by accountid = " + accountid + " and accountname = " + accountname + " is " + JwtToken + " =========");
        log.debug("============== Exit getJwtToken ==============");
        return JwtToken;
    }


//    public static Claims getMemberJwtClaims(HttpServletRequest request, String accountType) {
//        log.info("============== Enter getMemberJwtClaims (HttpRequest) ==============");
//        String tokenKey = accountType.equals(ACCOUNT_TYPE_ADMIN)? "adminToken" : "token";
//        String jwtToken = request.getHeader(tokenKey);
//        return getMemberJwtClaims(jwtToken, accountType);
//    }

    public static Claims getMemberJwtClaims(String jwtToken) {
        log.info("============== Enter getMemberJwtClaims (String) ==============");
        if(StringUtils.hasText(jwtToken)){
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(USER_APP_SECRET).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            log.info("========= Get token content accountid = " + claims.get("accountid").toString() + " and accountname = " + claims.get("accountname").toString() + ", expiration time = " + claims.getExpiration() + " =========");
            return claims;
//            return Long.parseLong(claims.get("accountid").toString());
        } else {
            log.error("Jwt token is empty.");
            return null;
        }
    }
}
