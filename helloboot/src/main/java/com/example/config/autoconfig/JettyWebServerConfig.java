package com.example.config.autoconfig;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.example.config.MyAutoConfiguration;
import com.example.config.autoconfig.JettyWebServerConfig.JettyCondition;

@MyAutoConfiguration
@Conditional(JettyCondition.class)
public class JettyWebServerConfig {

    @Bean("jettyWebServerFactory")
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }

    static class JettyCondition implements Condition {

        /**
         * ConditionContext: 현재 스프링 컨테이너와 애플리케이션이 돌아가는 환경의 정보를 제공
         * AnnotatedTypeMetadata: 주어진 클래스에 대한 애노테이션 정보를 추출
         */
        @Override
        public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
            return true;
        }

    }

}
