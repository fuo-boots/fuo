package cn.night.fuo.data;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableJpaRepositories(considerNestedRepositories = false,
//        entityManagerFactoryRef = "entityManagerFactory",
//        transactionManagerRef="transactionManager",
//        basePackages = {"cn.night.project"})
//@EnableTransactionManagement
public class Config {
    @Bean
//    @Primary
    DataSource dataSource() {
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


        return DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/fuo_test_main")
                .username("root")
                .password("12abAB")
                .build();

    }

    @Bean(name="entityManagerFactory")
//    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
        hibernateProperties.setProperty("hibernate.show-sql","true");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
//        factoryBean.setPersistenceUnitRootLocation("simple-persistence");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties);
        factoryBean.setPackagesToScan("cn.night","cn.night.project.fuo.test.data.jdbc","cn.night.fuo.data.entities");

//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "create");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }

    @Bean(name="transactionManager")
//    @Primary
    PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
