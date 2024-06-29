package com.example.backend_assingment.utils;

import com.example.backend_assingment.dto.AuthPermissions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class TokenUtils {

    private TokenUtils() {}

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.time}")
    private long validityInHours;

    public String generateToken(String username, String firstName, String lastName, String email, String mobile,
        AuthPermissions authPermission) {
        Instant now = Instant.now();

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(validityInHours, ChronoUnit.HOURS)))
            .claim("firstName", firstName)
            .claim("lastName", lastName)
            .claim("email", email)
            .claim("mobile", mobile)
            .claim("verifiedOn", new Date())
            .claim("permission", authPermission.name())
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateToken(String token, AuthPermissions[] permissions) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Jws<Claims> claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            Date expiration = claims.getBody().getExpiration();
            Date verifiedOn = claims.getBody().get("verifiedOn", Date.class);
            Instant twentyFourHoursAgo = Instant.now().minus(validityInHours, ChronoUnit.HOURS);

            return expiration.after(new Date()) && verifiedOn.after(Date.from(twentyFourHoursAgo));

        } catch (Exception e) {
            return false;
        }
    }
    public String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Jws<Claims> claims = Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
        return  claims.getBody().getSubject();
    }


}
