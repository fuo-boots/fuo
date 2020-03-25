package cn.night.fuo.data;

import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;

public class DataStarter {

//    @Autowired
//    private PlatformTransactionManager platformTransactionManager;

    @Bean(value = "cn.night.fuo.data.DataEnvironment")
    public DataEnvironment buildDataEnvironment() throws FuoEnvironmentInitializeFailedException , BeansException {
        DataEnvironment dataEnvironment = new DataEnvironment();
        dataEnvironment.build();

//        dataEnvironment.getDataSources().forEach(dataSource->{
//            SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory()
//                    .configureBean(dataSource,"name");
//        });

        return dataEnvironment;
    }

//    @Bean
//    public org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration buildHibernateJpaConfiguration() {
//        HibernateJpaAutoConfiguration hibernateJpaAutoConfiguration = new HibernateJpaAutoConfiguration();
//        hibernateJpaAutoConfiguration.
//    }
}
