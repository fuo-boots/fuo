package cn.night.fuo.persistent.mybatis;

import org.springframework.context.annotation.Bean;

public class MybatisConfig {
    @Bean
    public MybatisBeanPostProcessor mybatisBeanPostProcessor() {
        return new MybatisBeanPostProcessor();
    }
}
