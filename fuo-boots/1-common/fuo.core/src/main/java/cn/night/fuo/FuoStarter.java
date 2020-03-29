package cn.night.fuo;

import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.pattern.asserts.Assert;
import cn.night.fuo.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FuoStarter {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private FuoConf conf;

    @ConditionalOnMissingBean(name = "fuo.FuoEnvironment")
    @Bean(value = "fuo.FuoEnvironment")
    public FuoEnvironment getBean() throws FuoEnvironmentInitializeFailedException {
        try {
            log.debug("fuo environment initialize starting");
            Assert.notNull(this.conf, " FuoConf Ioc failed");
            FuoEnvironment environment = new FuoEnvironment();
            environment.build();
            log.debug("fuo environment initialize success");
            return environment;
        } catch (Exception e) {
            log.error("fuo environment initialize error", e);
            throw new FuoEnvironmentInitializeFailedException(e.getMessage(), e);
        }
    }

}
