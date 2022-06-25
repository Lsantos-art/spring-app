package com.springapp.springapp.configuration.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapp.springapp.configuration.constants.UserConstants;
import com.springapp.springapp.models.UserModel;

public class JwtAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private static final int EXPIRATION_TIME = 600_000;

    public JwtAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            UserModel user = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);

            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        } catch (Exception e) {
            throw new RuntimeException(UserConstants.API_AUTH_FAILED, e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException {

        DetailUserData userData = (DetailUserData) authResult.getPrincipal();
        String token = JWT.create().withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(UserConstants.API_AUTH_GUID_PASSWORD));

        response.getWriter().write(token);
        response.getWriter().flush();

    }

}
