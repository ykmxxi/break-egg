package com.example.loginpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.loginpractice.domain.User;
import com.example.loginpractice.domain.UserRole;
import com.example.loginpractice.jwt.JwtAuthenticationFilter;
import com.example.loginpractice.jwt.JwtAuthorizationFilter;
import com.example.loginpractice.repository.UserRepository;
import com.example.loginpractice.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final UserService userService;


    public SpringSecurityConfig(final UserService userService) {
        this.userService = userService;
    }

    /**
     * AuthenticationManager 빈 등록
     */
    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity http,
            final UserRepository userRepository
    ) throws Exception {

        http.httpBasic().disable();
        http.csrf().disable();
        http.rememberMe().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        AuthenticationManager authenticationManager = this.authenticationManager(
                http.getSharedObject(AuthenticationConfiguration.class)
        );
        // jwt filter
        http.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class
        ).addFilterBefore(
                new JwtAuthorizationFilter(userRepository),
                BasicAuthenticationFilter.class
        );

        http.authorizeRequests()
                .antMatchers("/jwt-login/info").authenticated()
                .antMatchers("/jwt-login/admin/**").hasRole(UserRole.ADMIN.name());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            }
            return user;
        };
    }

}
