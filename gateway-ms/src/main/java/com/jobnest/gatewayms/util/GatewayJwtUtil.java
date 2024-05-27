package com.jobnest.gatewayms.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class GatewayJwtUtil {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static final Logger log = LoggerFactory.getLogger(GatewayJwtUtil.class);

    public boolean validateAccessToken(String token) {
        log.info("Executing validateAccessToken()");

        try {
            Claims claims = getClaims(token);
            return claims != null && !isExpired(claims);
        } catch (ExpiredJwtException ex) {
            log.error("Token expired: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token is null, empty or only whitespace: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Token is invalid: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Token not supported: {}", ex.getMessage());
        } catch (SignatureException ex) {
            log.error("Signature validation failed");
        }
        return false;
    }

    public boolean isExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        boolean result = expiration.after(new Date(System.currentTimeMillis()));
        if (result) {
            log.info("Token not expired: {}", result);
            return false;
        }
        return true;
    }

    private Claims getClaims(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
            if (claims != null || claims.isEmpty()) {
                return claims;
            } else
                return claims;
        } catch (JwtException ex) {
            return null;
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}