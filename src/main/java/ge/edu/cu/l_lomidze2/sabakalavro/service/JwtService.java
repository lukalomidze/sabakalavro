package ge.edu.cu.l_lomidze2.sabakalavro.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {
    private SecretKey key;

    @Value("${app.authentication.expiration}")
    private Duration expiration;

    public JwtService(@Value("${app.authentication.secret-key}") String key) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String encode(Authentication authentication) {
        return Jwts.builder()
            .subject(authentication.getName())
            .claim("role", authentication.getAuthorities().iterator().next().getAuthority())
            .expiration(Date.from(Instant.now().plus(expiration)))
            .id(UUID.randomUUID().toString())
        .signWith(key).compact();
    }

    public Claims decode(String token) {
        return Jwts.parser()
            .verifyWith(key)
        .build()
            .parseSignedClaims(token)
        .getPayload();
    }

    public Claims fromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return decode(authHeader.split(" ")[1]);
    }
}
