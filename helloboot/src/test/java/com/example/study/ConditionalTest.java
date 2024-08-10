package com.example.study;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

class ConditionalTest {

    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner();
    }

    @Test
    void conditionalTrue() {
        contextRunner.withUserConfiguration(Config1.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(Config1.class);
                    assertThat(context).hasSingleBean(MyBean.class);
                });
    }

    @Test
    void conditionalFalse() {
        contextRunner.withUserConfiguration(Config2.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(Config2.class);
                    assertThat(context).doesNotHaveBean(MyBean.class);
                });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class) @interface BooleanConditional {

        boolean value();

    }

    @Configuration
    @BooleanConditional(true)
    static class Config1 {

        @Bean
        MyBean myBean() {
            return new MyBean();
        }

    }

    @Configuration
    @BooleanConditional(false)
    static class Config2 {

        @Bean
        MyBean myBean() {
            return new MyBean();
        }

    }

    static class MyBean {}

    static class BooleanCondition implements Condition {

        @Override
        public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
            // AnnotatedTypeMetadata로 지정한 애노테이션의 엘리먼트(attribute) 정보를 가져옴
            Map<String, Object> annotationAttributes =
                    metadata.getAnnotationAttributes(BooleanConditional.class.getName());

            return (Boolean) annotationAttributes.get("value");
        }

    }

}
