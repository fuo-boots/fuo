package cn.night.fuo.data;

import cn.night.fuo.data.core.AbdEnableJpaRepositories;
import cn.night.fuo.data.core.AbdJpaRepositoriesRegistrar;
import cn.night.fuo.data.repository.BaseRepositoryFactoryBean;
import cn.night.fuo.spring.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration


@EnableJpaRepositories(considerNestedRepositories = false,
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class,
//        entityManagerFactoryRef = "entityManagerFactoryMaster",//配置连接工厂 entityManagerFactory
//        transactionManagerRef = "transactionManagerMaster", //配置 事物管理器  transactionManager
        basePackages = "cn.night.project.fuo.quickstart.jdbc.main") //cn.night.fuo.data.repository

//@EnableTransactionManagement
public class Config {
    @Autowired
    private DataEnvironment dataEnvironment;

    @Autowired
    @Qualifier("master")
    private DataSource dataSource;

    @Bean("entityManagerFactory") //"entityManagerFactoryMaster"
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
        hibernateProperties.setProperty("hibernate.show-sql","true");


        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
//        factoryBean.setPersistenceUnitRootLocation("simple-persistence");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties);
        factoryBean.setPackagesToScan("cn.night.project.fuo.quickstart.jdbc.entities");

//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "create");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        factoryBean.setJpaProperties(properties);

        return factoryBean;

    }

    @Bean()//"transactionManagerMaster"
    @Primary
    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager(factory);
        LocalContainerEntityManagerFactoryBean factoryBean = entityManagerFactoryBean();
        return new JpaTransactionManager(factoryBean.getObject());
    }

//    @Bean(name="entityManagerFactoryMain")
////    @Primary
//    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
////        DataSource dataSource = SpringContextHolder.getBean("mainDataSource");
//
////        DataSource dataSource = DataSourceBuilder.create()
////                .driverClassName("com.mysql.jdbc.Driver")
////                .url("jdbc:mysql://localhost:3306/fuo_test_main")
////                .username("root")
////                .password("12abAB@1986")
////                .build();
//
//        final Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
//        hibernateProperties.setProperty("hibernate.show-sql","true");
//
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(dataSource);
////        factoryBean.setPersistenceUnitRootLocation("simple-persistence");
//        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        factoryBean.setJpaProperties(hibernateProperties);
//        factoryBean.setPackagesToScan("cn.night.project.fuo.quickstart.jdbc.entities");
//
////        Properties properties = new Properties();
////        properties.setProperty("hibernate.hbm2ddl.auto", "create");
////        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
////        factoryBean.setJpaProperties(properties);
//
//        return factoryBean;
//    }
//
//    @Bean(name="transactionManagerMain")
////    @Primary
//    PlatformTransactionManager transactionManager() {
//
//        EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactoryMain");
//        return new JpaTransactionManager(emf);
//    }
}
