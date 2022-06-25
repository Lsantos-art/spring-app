package com.springapp.springapp.configuration.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springapp.springapp.configuration.constants.UserConstants;

public class JwtValidateFilter extends BasicAuthenticationFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    public JwtValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(AUTHORIZATION_HEADER_PREFIX, "");

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(UserConstants.API_AUTH_GUID_PASSWORD)).build().verify(token)
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }

        return null;
    }
}
