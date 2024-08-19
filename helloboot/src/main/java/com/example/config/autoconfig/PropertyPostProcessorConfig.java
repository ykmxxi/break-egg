package com.example.config.autoconfig;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

import com.example.config.MyAutoConfiguration;
import com.example.config.MyConfigurationProperties;

@MyAutoConfiguration
public class PropertyPostProcessorConfig {

    @Bean
    BeanPostProcessor propertyPostProcessor(final Environment env) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(final Object bean, final String beanName)
                    throws BeansException {
                MyConfigurationProperties annotation = AnnotationUtils.findAnnotation(
                        bean.getClass(), MyConfigurationProperties.class);
                if (annotation == null) {
                    return bean;
                }
                Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(annotation);
                String prefix = (String) attributes.get("prefix");

                return Binder.get(env)
                        .bindOrCreate(prefix, bean.getClass());
            }
        };
    }

}
