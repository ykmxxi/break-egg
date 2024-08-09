package com.example.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class ConfigurationTest {

    @Test
    @DisplayName("@Configuration(proxyBeanMethods = true): default 설정, 프록시를 사용해 싱글톤 빈")
    void configuration() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);
        Common common = ac.getBean(Common.class);

        assertThat(bean1.common).isSameAs(bean2.common);
        assertThat(bean1.common).isSameAs(common);
        assertThat(bean2.common).isSameAs(common);
    }

    @Test
    @DisplayName("Proxy를 이용해 싱글톤 빈 구현")
    void proxyCommonMethod() {
        MyConfigProxy myConfigProxy = new MyConfigProxy();

        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        assertThat(bean1.common).isSameAs(bean2.common);
    }

    @Test
    @DisplayName("@Configuration(proxyBeanMethods = false): 일반적인 Java처럼 동작")
    void proxyBeanMethodsFalse() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig2.class);
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);
        Common common = ac.getBean(Common.class);

        assertThat(bean1.common).isNotSameAs(bean2.common);
        assertThat(bean1.common).isNotSameAs(common);
        assertThat(bean2.common).isNotSameAs(common);
    }

    // 일반적인 빈 오브젝트는 싱글톤 빈처럼 동작: 동일한 common 객체 주입
    // Bean1 <-- common
    // Bean2 <-- common
    static class MyConfigProxy extends MyConfig {

        private Common common;

        @Override
        Common common() {
            if (this.common == null) {
                this.common = super.common();
            }
            return this.common;
        }

    }

    @Configuration
    static class MyConfig {

        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }

    }

    @Configuration(proxyBeanMethods = false)
    static class MyConfig2 {

        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }

    }

    static class Bean1 {

        private final Common common;

        public Bean1(final Common common) {
            this.common = common;
        }

    }

    static class Bean2 {

        private final Common common;

        public Bean2(final Common common) {
            this.common = common;
        }

    }

    static class Common {}

}
