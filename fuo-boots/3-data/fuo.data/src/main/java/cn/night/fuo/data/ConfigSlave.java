//package cn.night.fuo.data;
//
//import cn.night.fuo.data.core.AbdEnableJpaRepositories;
//import cn.night.fuo.spring.SpringContextHolder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//@EnableJpaRepositories(considerNestedRepositories = false,
////        entityManagerFactoryRef="entityManagerFactorySlave",
////        transactionManagerRef = "transactionManagerSlave",
//        basePackages = "cn.night.project.fuo.quickstart.jdbc.slave") //cn.night.project.fuo.quickstart.jdbc.slave
//
////@EnableTransactionManagement
//public class ConfigSlave {
//    @Autowired
//    private DataEnvironment dataEnvironment;
//
//    @Autowired
//    @Qualifier("slave")
//    private DataSource dataSource;
//
//    @Autowired
//    private JpaProperties jpaProperties;
//
//
//    @Bean("entityManagerFactorySlave")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
//        final Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
//        hibernateProperties.setProperty("hibernate.show-sql","true");
//
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
//
//    }
//
//
////    @Bean("entityManagerSlave")
////    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
////        return localContainerEntityManagerFactoryBean(builder).getObject().createEntityManager();
////    }
//
//    @Bean("transactionManagerSlave")
//    public PlatformTransactionManager transactionManager() {
//        LocalContainerEntityManagerFactoryBean factoryBean = entityManagerFactoryBean();
//        return new JpaTransactionManager(factoryBean.getObject());
//    }
//
//
////
////    @Bean(name="entityManagerFactorySlave")
//////    @Primary
////    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//////        DataSource dataSource = SpringContextHolder.getBean("mainDataSource");
////
//////        DataSource dataSource = DataSourceBuilder.create()
//////                .driverClassName("com.mysql.jdbc.Driver")
//////                .url("jdbc:mysql://localhost:3306/fuo_test_slave")
//////                .username("root")
//////                .password("12abAB@1986")
//////                .build();
////
////        final Properties hibernateProperties = new Properties();
////        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
////        hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
////        hibernateProperties.setProperty("hibernate.show-sql","true");
////
////        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
////        factoryBean.setDataSource(dataSource);
//////        factoryBean.setPersistenceUnitRootLocation("simple-persistence");
////        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
////        factoryBean.setJpaProperties(hibernateProperties);
////        factoryBean.setPackagesToScan("cn.night.project.fuo.quickstart.jdbc.entities");
////
//////        Properties properties = new Properties();
//////        properties.setProperty("hibernate.hbm2ddl.auto", "create");
//////        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//////        factoryBean.setJpaProperties(properties);
////
////        return factoryBean;
////    }
////
////    @Bean(name="transactionManagerSlave")
//////    @Primary
////    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
////        return new JpaTransactionManager(entityManagerFactoryBean(builder).getObject());
////
////        EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactorySlave");
////        return new JpaTransactionManager(emf);
////    }
//}
