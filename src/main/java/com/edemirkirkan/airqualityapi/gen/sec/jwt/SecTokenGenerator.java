package com.edemirkirkan.airqualityapi.gen.sec.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SecTokenGenerator {

    @Value("${sec.expire.time}")
    private Long EXPIRE_TIME;

    @Value("${sec.app.key}")
    private String APP_KEY;

    public String generateJwtToken(Authentication authentication) {
        SecUserDetails secUserDetails = (SecUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);

        String token = Jwts.builder()
                .setSubject(secUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_KEY)
                .compact();

        return token;
    }

    public String findUsernameByToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws.getBody().getSubject();
    }

    public boolean validateToken(String token) {
        boolean isValid = true;

        try {
            Jws<Claims> claimsJws = parseToken(token);
            boolean tokenExpired = isTokenExpired(claimsJws);
            isValid = !tokenExpired;
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(APP_KEY)
                .parseClaimsJws(token);
        return claimsJws;
    }

    private boolean isTokenExpired(Jws<Claims> claimsJws) {
        Date expireDate = claimsJws.getBody().getExpiration();
        return expireDate.before(new Date());
    }
}
