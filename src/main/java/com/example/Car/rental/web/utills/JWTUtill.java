package com.example.Car.rental.web.utills;

import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtill {

    private static final String SECRET_KEY = "xW3h7PtX/qvE+MziRQ1/AS1x1QzUkYVYd6VZk1Hd6aQl"; // Replace with a secure secret key
    private static final long TOKEN_EXPIRATION = 1000 * 60 * 60 * 10; // 10 hours
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    // Extracts username (subject) from the token
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Generates a token for the given UserDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generates a token with additional claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return createToken(extraClaims, userDetails.getUsername(), TOKEN_EXPIRATION);
    }

    // Generates a refresh token with additional claims
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return createToken(extraClaims, userDetails.getUsername(), REFRESH_TOKEN_EXPIRATION);
    }

    // Validates the token against the user details
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Checks if the token has expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extracts expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    // Extracts a specific claim using a resolver function
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extracts all claims from the token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Creates a JWT with the specified claims and expiration
    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(signInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Provides the signing key
    private Key signInKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }
}

