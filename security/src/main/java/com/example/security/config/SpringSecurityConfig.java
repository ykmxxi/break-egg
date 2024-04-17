package com.example.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.security.user.User;
import com.example.security.user.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // WebSecurityConfigurer 에 Spring Security 구성을 정의
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final UserService userService;

    /**
     * 정적 리소스는 spring security 대상에서 제외
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//    CSS(new String[]{"/css/**"}),
//    JAVA_SCRIPT(new String[]{"/js/**"}),
//    IMAGES(new String[]{"/images/**"}),
//    WEB_JARS(new String[]{"/webjars/**"}),
//    FAVICON(new String[]{"/favicon.*", "/*/icon-*"});
        return (web) -> web.ignoring() // 정적 리소스의 일반적인 위치들에 대한 요청을 무시
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // basic authentication
        http.httpBasic().disable(); // basic authentication filter 비활성화

        // csrf(사이트 위변조 요청 방지)
        http.csrf();

        http.rememberMe(); // 토큰 기반 기억 인증을 허용하는 방법, 로그인 유지 기능에 사용
        // 브라우저를 종료해도 위 RememberMe 토큰은 남아있음, 반대로 JSESSIONID 쿠키는 브라우저 종료시 삭제

        http.authorizeRequests() // 인가(Authorization) 설정, 경로별로 권한을 설정
                // 홈화면, 회원가입 화면은 모두에게 허용
                .antMatchers("/", "/home", "/signup").permitAll()
                .antMatchers("/note").hasRole("USER") // 개인 노트 페이지는 일반 유저에게만 허용
                .antMatchers("/admin").hasRole("ADMIN") // 관리자 페이지는 관리자에게만 허용
                .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN") // 공지사항 POST 요청(글 쓰기) 관리자만 허용
                .antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN") // 공지사항 삭제 요청 관리자만 허용
                .anyRequest().authenticated(); // anyRequest(): 위 요청을 제외한 나머지 요청은 인증된 사용자만 접근 가능

        http.formLogin() // 폼 로그인
                .loginPage("/login") // 로그인 페이지 설정
                .defaultSuccessUrl("/") // 인증 성공 후 redirect url
                .permitAll(); // 로그인 요청은 누구나 할 수 있음, 따로 인증이 필요한 요청이 아님

        // 별도의 로그아웃 기능을 구현하지 않아도 됨
        http.logout() // 로그아웃이 발생하도록 트리거하는 RequestMatcher
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return user;
        };
    }

}
