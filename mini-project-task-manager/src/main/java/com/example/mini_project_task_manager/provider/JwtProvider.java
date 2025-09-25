package com.example.mini_project_task_manager.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component

public class JwtProvider {

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String CLAIM_ROLES = "roles";

    private final SecretKey key;
    private final long jwtExpirationMs;
    private final long jwtEmailExpirationMs;
    private final int clockSkewSeconds;

    private final JwtParser parser;

    public JwtProvider(

            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long jwtExpirationMs,
            @Value("${jwt.email-expiration}") long jwtEmailExpirationMs,
            @Value("${jwt.clock-skew-seconds:0}") int clockSkewSeconds) {


        byte[] secretBytes = Decoders.BASE64.decode(secret);
        if (secretBytes.length < 32) {

            throw new IllegalArgumentException("jwt.secret은 항상 256 비트 이상을 권장합니다.");
        }

        this.key = Keys.hmacShaKeyFor(secretBytes);
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtEmailExpirationMs = jwtEmailExpirationMs;
        this.clockSkewSeconds = Math.max(clockSkewSeconds, 0);
        this.parser = Jwts.parser()
                .verifyWith(this.key)
                .build();
    }

    public String generateJwtToken(String username, Set<String> roles) {
        long now = System.currentTimeMillis();
        Date iat = new Date(now);
        Date exp = new Date(now + jwtExpirationMs);

        List<String> roleList = (roles == null) ? List.of() : new ArrayList<>(roles);

    return Jwts.builder()
            .setSubject(username)           // 유저 이름
            .claim(CLAIM_ROLES, roleList)          // 권한도 토큰 안에 같이 저장
            .setIssuedAt(iat)
            .setExpiration(exp)
            .signWith(key)
            .compact();

    }

    public String generateEmailJwtToken(String email) {
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtEmailExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            throw new IllegalArgumentException("Authorization 형식이 올바르지 않습니다.");
        }

        return bearerToken.substring(BEARER_PREFIX.length()).trim();
    }

    private Claims parseClaimsInternal(String token, boolean allowClockSkewOnExpiry) {

        try {
            return parser.parseSignedClaims(token).getPayload();

        } catch (ExpiredJwtException ex) {

            if (allowClockSkewOnExpiry && clockSkewSeconds > 0 && ex.getClaims() != null) {

                Date exp = ex.getClaims().getExpiration();
                if (exp != null) {
                    long skewMs = clockSkewSeconds * 1000L;
                    long now = System.currentTimeMillis();
                    if (now - exp.getTime() <= skewMs) {

                        return ex.getClaims();
                    }
                }
            }
            throw ex;
        }
    }

    public boolean isValidToken(String tokenWithoutBearer) {

        try {
            parseClaimsInternal(tokenWithoutBearer, true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String tokenWithoutBearer) {

        return parseClaimsInternal(tokenWithoutBearer, true);
    }

    public String getUsernameFromJwt(String tokenWithoutBearer) {
        return getClaims(tokenWithoutBearer).getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRolesFromJwt(String tokenWithoutBearer) {

        Object raw = getClaims(tokenWithoutBearer).get(CLAIM_ROLES);
        if (raw == null)
            return Set.of();

        if (raw instanceof List<?> list) {
            Set<String> result = new HashSet<>();
            for (Object o : list)
                if (o != null)
                    result.add(o.toString());
            return result;
        }

        if (raw instanceof Set<?> set) {
            Set<String> result = new HashSet<>();
            for (Object o : set)
                if (o != null)
                    result.add(o.toString());
            return result;
        }

        return Set.of(raw.toString());
    }

    public String getEmailFromJwt(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }

    public long getRemainingMillis(String tokenWithoutBearer) {
        Claims c = parseClaimsInternal(tokenWithoutBearer, true);
        return c.getExpiration().getTime() - System.currentTimeMillis();
    }

}