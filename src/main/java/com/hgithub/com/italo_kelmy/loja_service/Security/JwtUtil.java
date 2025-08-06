package com.hgithub.com.italo_kelmy.loja_service.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    public String generateKey(UserDetails user) {
        return Jwts.builder()
                .signWith(jwtConfig.secretKey())
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .compact();
    }


    public Claims extrailAllClaims(String key) {
        return Jwts.parser()
                .verifyWith(jwtConfig.secretKey())
                .build()
                .parseSignedClaims(key)
                .getPayload();
    }

    public <T> T extrairClaims(String key, Function<Claims, T> resolver) {
        return resolver.apply(extrailAllClaims(key));
    }

    public boolean validadeToken(String key, UserDetails user) {
        String usuario = extrairClaims(key, Claims::getSubject);
        return usuario.equals(user.getUsername()) && isValidation(key);
    }

    private boolean isValidation(String key) {
        return extrairClaims(key, Claims::getExpiration).after(new Date());
    }


}
