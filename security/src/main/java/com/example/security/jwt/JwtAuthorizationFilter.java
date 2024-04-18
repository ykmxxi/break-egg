package com.example.security.jwt;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security.user.User;
import com.example.security.user.UserRepository;

/**
 * JWT를 이용한 인가 필터
 * 1. Cookie에서 JWT Token을 구함
 * 2. JWT Token을 파싱해 username을 구함
 * 3. username으로 User를 구하고 Authentication을 생성
 * 4. 생성된 Authentication을 SecurityContext에 넣음
 * 5. Exception이 발생하면 응답의 쿠키를 null로 변경
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        String token = null;

        // cookie에서 JWT Token 가져오기
        try {
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        } catch (Exception ignored) {
        }

        if (token != null) {
            try {
                Authentication authentication = getUsernamePasswordAuthenticationToken(token);
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            } catch (Exception e) {
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * JWT Token으로 User를 찾아 UsernamePasswordAuthenticationToken 반환
     * User가 없으면 null
     */
    private Authentication getUsernamePasswordAuthenticationToken(final String token) {
        String username = JwtUtils.getUsername(token);
        if (username != null) {
            User user = userRepository.findByUsername(username);
            return new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
        }

        return null;
    }

}
