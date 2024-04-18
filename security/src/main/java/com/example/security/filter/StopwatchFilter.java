package com.example.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * 1개의 요청이 들어온 시점부터 끝날 때 까지 걸린 시간을 기록
 */
@Slf4j
public class StopwatchFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        StopWatch stopWatch = new StopWatch(request.getServletPath());

        stopWatch.start();
        filterChain.doFilter(request, response);
        stopWatch.stop();

        log.info("요청-응답 시간: {}", stopWatch.shortSummary());
    }

}
