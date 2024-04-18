package com.example.security.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.user.User;

/**
 * JWT 로그인 인증 필터
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    /**
     * 로그인 인증 시도
     */
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

    /**
     * 인증 성공시 사용
     * JWT Token을 생성해 쿠키에 넣음
     */
    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult
    ) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal(); // 인증 정보에서 principal(user)을 가져옮
        String jwtToken = JwtUtils.createToken(user); // JWT Token 생성

        // 쿠키 생성
        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, jwtToken); // 이름과 토큰을 넣어 쿠키 생성
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키 유효시간 설정
        cookie.setPath("/"); // 클라이언트가 쿠키를 반환해야 하는 쿠키의 경로를 지정, "/" 하위 모든 경로

        response.addCookie(cookie); // 응답에 쿠키를 넣음
        response.sendRedirect("/"); // 로그인 성공 후 리다이렉션
    }

    /**
     * 인증 실패시 사용
     * 로그인 페이지로 리다이렉션
     */
    @Override
    protected void unsuccessfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException failed
    ) throws IOException, ServletException {
        response.sendRedirect("/login");
    }

}
