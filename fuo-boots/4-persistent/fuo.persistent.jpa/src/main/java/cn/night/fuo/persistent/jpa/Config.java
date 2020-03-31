package cn.night.fuo.persistent.jpa;

import cn.night.fuo.persistent.jpa.repository.FuoRepositoryFactoryBean;
import cn.night.fuo.persistent.jpa.repository.FuoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.query.ExtensionAwareQueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(considerNestedRepositories = false,
        repositoryFactoryBeanClass = FuoRepositoryFactoryBean.class,
        entityManagerFactoryRef = "entityManagerFactoryMaster",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerMaster", //配置 事物管理器  transactionManager
        basePackages = "cn.night") //cn.night.fuo.data.repository
public class Config {

//    @PersistenceContext
//    private EntityManager entityManager;
//    @Autowired
//    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("master")
    private DataSource dataSource;

    public Config(){

    }

//    public  void start(){
//        QueryMethodEvaluationContextProvider evaluationContextProvider = new ExtensionAwareQueryMethodEvaluationContextProvider(
//                applicationContext);
//
//        JpaRepositoryFactoryBean<?, ?, ?> factory = new FuoRepositoryFactoryBean<UserRepository, SecurityProperties.User, Integer>(
//                FuoRepositoryImpl.class);
//    }

    @ConditionalOnMissingBean(name = "entityManagerFactoryMaster")
    @Bean("entityManagerFactoryMaster") //  "entityManagerFactory"
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
        factoryBean.setPackagesToScan("cn.night.project");

        return factoryBean;

    }

    @ConditionalOnMissingBean(name = "transactionManagerMaster")
    @Bean("transactionManagerMaster")//
    @Primary
    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager(factory);
        LocalContainerEntityManagerFactoryBean factoryBean = entityManagerFactoryBean();
        return new JpaTransactionManager(factoryBean.getObject());
    }

}
