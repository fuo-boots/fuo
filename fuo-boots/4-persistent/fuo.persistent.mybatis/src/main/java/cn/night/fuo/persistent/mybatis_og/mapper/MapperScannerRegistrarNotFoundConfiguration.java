package cn.night.fuo.persistent.mybatis_og.mapper;

import cn.night.fuo.persistent.mybatis_og.MybatisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Import;

@org.springframework.context.annotation.Configuration
@Import({ MybatisAutoConfiguration.AutoConfiguredMapperScannerRegistrar.class })
@ConditionalOnMissingBean(MapperFactoryBean.class)
@Slf4j
public class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("No {} found.", MapperFactoryBean.class.getName());
    }
}
