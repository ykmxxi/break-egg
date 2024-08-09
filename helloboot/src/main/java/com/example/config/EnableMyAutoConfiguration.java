package com.example.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.example.config.autoconfig.DispatcherServletConfig;
import com.example.config.autoconfig.TomcatWebServerConfig;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // class, interface, enum
@Import({DispatcherServletConfig.class, TomcatWebServerConfig.class}) // 외부 패키지의 클래스를 등록
public @interface EnableMyAutoConfiguration {
}
