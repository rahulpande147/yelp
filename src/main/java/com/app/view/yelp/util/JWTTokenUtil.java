package com.app.view.yelp.util;

import com.app.view.yelp.service.JwtUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

@Component
public class JWTTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.validity}")
    private int JWT_TOKEN_VALIDITY;

    private String userName;
    private Date expiration;

    public String getUserNameFromToken(String iToken){
        return getTokenClaims(iToken).getSubject();
    }

    public Date getTokenExpiration(String iToken) {
        return getTokenClaims(iToken).getExpiration();
    }

    private Claims getTokenClaims(String iToken) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(iToken).getBody();
    }


    public String generateToken(UserDetails iUserDetails) {
        return Jwts.builder().addClaims(new HashMap<>()).setSubject(iUserDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public boolean validateToken(String iToken, UserDetails iUserDetails) {
        Claims claims = getTokenClaims(iToken);
        return claims.getExpiration().after(new Date()) && claims.getSubject().equalsIgnoreCase(iUserDetails.getUsername());
    }

}
