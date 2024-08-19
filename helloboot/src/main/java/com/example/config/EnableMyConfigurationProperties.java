package com.example.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

// Enable로 시작하는 애노테이션의 목적은 대부분 안에 @Import를 넣어 Config 파일을 가져오거나,
// Import Selector를 가져오게 만드는게 목적
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyConfigurationPropertiesImportSelector.class)
public @interface EnableMyConfigurationProperties {

    Class<?> value();

}
