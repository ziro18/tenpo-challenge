package com.tenpo.challenge.security;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration}")
    private int jwtExpiration;

    public String generateToken(Authentication authentication) {

        return  Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean tokenValidator(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new RuntimeException(" JWT firm not valid");
        } catch (MalformedJwtException ex) {
            throw new RuntimeException(" JWT token not valid");
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException(" JWT token expired");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException(" JWT token not compatible");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(" JWT claims is empty");
        }
    }
}
