package com.example.security.filter;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.user.User;

/**
 * tester 유저에게 어드민과 유저 권한을 모두 준다.
 */
public class TesterAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public TesterAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws AuthenticationException {
        Authentication authentication = super.attemptAuthentication(request, response);
        User user = (User) authentication.getPrincipal();

        if (user.getUsername().startsWith("tester")) {
            return new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Stream.of("ROLE_ADMIN", "ROLE_USER")
                            .map(authority -> (GrantedAuthority) () -> authority)
                            .toList()
            );
        }

        return authentication;
    }

}
