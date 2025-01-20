package com.phonepe.v1.PhonePe.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final String TOKEN_CACHE_KEY = "jwt_token:";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public void cacheToken(String phoneNumber, String token) {
        String cacheKey = TOKEN_CACHE_KEY + phoneNumber;
        redisTemplate.opsForValue().set(cacheKey, token,Duration.ofHours(24));
    }

    public void invalidateToken(String phoneNumber) {
        String cacheKey = TOKEN_CACHE_KEY + phoneNumber;
        redisTemplate.delete(cacheKey);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        String isToken = (String) redisTemplate.opsForValue().get(TOKEN_CACHE_KEY+userDetails.getUsername());
        if(isToken==null) {
            String token = Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            cacheToken(userDetails.getUsername(),token);
            return token;
        }
        return isToken;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String cacheKey = TOKEN_CACHE_KEY + extractUsername(token);
        String cachedToken = (String) redisTemplate.opsForValue().get(cacheKey);
        return cachedToken != null && cachedToken.equals(token) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String refreshToken(String token) {
        final Claims claims = extractAllClaims(token);
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}