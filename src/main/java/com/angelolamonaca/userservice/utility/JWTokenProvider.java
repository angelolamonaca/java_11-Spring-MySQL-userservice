package com.angelolamonaca.userservice.utility;

import com.angelolamonaca.userservice.domain.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 17/10/2021
 */
public class JWTokenProvider {
    private static final Algorithm algorithm = Algorithm.HMAC256(System.getenv("secret").getBytes());

    static public Map<String, String> generateTokens(com.angelolamonaca.userservice.domain.User user, String requestURL) {
        String access_token = generateAccessToken(user, requestURL);
        String refresh_token = generateRefreshToken(user, requestURL);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        return tokens;
    }

    static public Map<String, String> generateTokens(org.springframework.security.core.userdetails.User user, String requestURL) {
        String access_token = generateAccessToken(user, requestURL);
        String refresh_token = generateRefreshToken(user, requestURL);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        return tokens;
    }

    static private String generateAccessToken(com.angelolamonaca.userservice.domain.User user, String requestURL) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(requestURL)
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
    }

    static private String generateAccessToken(org.springframework.security.core.userdetails.User user, String requestURL) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(requestURL)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    static private String generateRefreshToken(com.angelolamonaca.userservice.domain.User user, String requestURL) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000))
                .withIssuer(requestURL)
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
    }

    static private String generateRefreshToken(org.springframework.security.core.userdetails.User user, String requestURL) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000))
                .withIssuer(requestURL)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    static public DecodedJWT decodeJWT(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

}
