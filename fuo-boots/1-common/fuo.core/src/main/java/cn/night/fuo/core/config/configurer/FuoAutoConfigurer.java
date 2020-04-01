package cn.night.fuo.core.config.configurer;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public interface FuoAutoConfigurer<T> {
    void init(T properties, BeanDefinitionRegistry registry) throws Exception;
    void configurer(T properties, BeanDefinitionRegistry registry) throws Exception;
}
