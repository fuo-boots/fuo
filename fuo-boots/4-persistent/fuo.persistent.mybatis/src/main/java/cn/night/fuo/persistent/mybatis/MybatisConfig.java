package cn.night.fuo.persistent.mybatis;

import cn.night.fuo.core.config.FuoAutoConfiguration;
import cn.night.fuo.persistent.mybatis.processor.MybatisBeanPostProcessor;
import cn.night.fuo.persistent.mybatis.registrar.MybatisAutoConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(MybatisProperties.class)
@FuoAutoConfiguration(properties = MybatisProperties.class, configurer = MybatisAutoConfigurer.class)
public class MybatisConfig {
    @Bean
    public MybatisBeanPostProcessor mybatisBeanPostProcessor() {
        return new MybatisBeanPostProcessor();
    }
}
