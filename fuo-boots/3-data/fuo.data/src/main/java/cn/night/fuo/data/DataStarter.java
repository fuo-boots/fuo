package cn.night.fuo.data;

import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.spring.SpringContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Import(Config.class)
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
//    public JpaProperties buildHibernateJpaConfiguration() {
//        //org.springframework.boot.autoconfigure.orm.jpa.
//
//        final Map<String, String> hibernateProperties = new HashMap<>();
//        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        hibernateProperties.put("hibernate.hbm2ddl.auto","false");
//        hibernateProperties.put("hibernate.show-sql","true");
//
//        JpaProperties jpaProperties = new JpaProperties();
//        jpaProperties.setShowSql(true);
//        jpaProperties.setProperties(hibernateProperties);
//        return jpaProperties;
//    }

//    @Bean
//    public DataSource build(){
//        DataSourceProperties dataSourceProperties = new DataSourceProperties();
//        dataSourceProperties.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSourceProperties.setUrl("jdbc:mysql://localhost:3306/fuo_test_main");
//        dataSourceProperties.setUsername("root");
//        dataSourceProperties.setPassword("12abAB");
//
//        javax.sql.DataSource dataSource = dataSourceProperties
//                .initializeDataSourceBuilder()
//                .type(com.zaxxer.hikari.HikariDataSource.class)
//                .build();
//        return dataSource;
//    }
}
