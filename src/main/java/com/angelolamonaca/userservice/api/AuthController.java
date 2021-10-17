package com.angelolamonaca.userservice.api;

import com.angelolamonaca.userservice.domain.PasswordConstraintValidator;
import com.angelolamonaca.userservice.domain.User;
import com.angelolamonaca.userservice.service.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.angelolamonaca.userservice.utility.JWTokenProvider.decodeJWT;
import static com.angelolamonaca.userservice.utility.JWTokenProvider.generateTokens;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 17/10/2021
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        List<String> passwordValidationResult = new PasswordConstraintValidator().validate(user.getPassword());
        if (!passwordValidationResult.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(passwordValidationResult);
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity
                .created(uri)
                .body(userService.saveUser(user));
    }

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = decodeJWT(token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                Map<String, String> tokens = generateTokens(user, request.getRequestURL().toString());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.sendError(FORBIDDEN.value());
            }
        } else {
            throw new RuntimeException("Refresh token is missing or malformed");
        }

    }
}
