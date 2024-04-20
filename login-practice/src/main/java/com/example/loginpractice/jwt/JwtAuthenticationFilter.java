package com.example.loginpractice.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.loginpractice.domain.User;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getParameter("username"),
                request.getParameter("password"),
                new ArrayList<>()
        );

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult
    ) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String jwtToken = JwtUtils.createToken(user, JwtProperties.SECRET_KEY);

        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, jwtToken);
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException failed
    ) throws IOException, ServletException {
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

}
