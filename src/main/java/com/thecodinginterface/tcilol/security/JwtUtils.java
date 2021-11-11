package com.thecodinginterface.tcilol.security;

import com.thecodinginterface.tcilol.models.UserDetailsDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Objects;

public class JwtUtils {

    public static String parseJwtFromHeader(String authHeader) {
        Objects.requireNonNull(authHeader);

        if (!authHeader.startsWith("Bearer")) {
            throw new IllegalArgumentException("Valid header starts with 'Bearer'");
        }

        String[] parts = authHeader.split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Auth header not of form 'Bearer token'");
        }

        return parts[1];
    }

    public static String parseUserNameFromJwtToken(String jwtSecret, String token) {
        return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    public static String generateJwtToken(String jwtSecret, int jwtExpiryMs, UserDetailsDto userDetails) {
        var now = new Date();

        return Jwts.builder()
                .setId(String.valueOf(userDetails.getId()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpiryMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
