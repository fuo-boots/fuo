package cn.night.fuo.data;

import cn.night.fuo.core.env.FuoEnvironmentBuilder;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.spring.SpringContextHolder;
import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
@Component(value = "cn.night.fuo.data.DataEnvironment")
public class DataEnvironment implements FuoEnvironmentBuilder {


//    private List<PlatformTransactionManager> dataSources = new ArrayList<>();



    @Override
    public void doBuild() throws FuoEnvironmentInitializeFailedException {

//        new EmbeddedDatabaseBuilder() //
//                .generateUniqueName(true) //
//                .setType(EmbeddedDatabaseType.HSQL) //
//                .setScriptEncoding("UTF-8") //
//                .ignoreFailedDrops(true) //
//                .build();

//        String name = "main";
//
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/fuo_test_slave")
                .username("root")
                .password("12abAB")
                .build();



        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
        hibernateProperties.setProperty("hibernate.show-sql","true");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
//        factoryBean.setPersistenceUnitRootLocation("simple-persistence");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties);
        factoryBean.setPackagesToScan("cn.night.project.fuo.quickstart.jdbc.slave");

//        SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory()
//                .autowireBean(factoryBean);
//                //.configureBean(factoryBean,name+"FactoryBean");

//        SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory()
//                .configureBean(dataSource,name+"FactoryBean");








//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName("com.mysql.jdbc.Driver");
//        config.setJdbcUrl("jdbc:mysql://localhost:3306/fuo_test_main");
//        config.setUsername("root");
//        config.setPassword("12abAB");

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
//
//
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//
//        final Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto","false");
//        hibernateProperties.setProperty("hibernate.show-sql","true");
//
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setPackagesToScan("cn.night");
//        factoryBean.setDataSource(dataSource);
//        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//        factoryBean.setJpaProperties(hibernateProperties);
//        factoryBean.setEntityManagerFactoryInterface();
//
//        PlatformTransactionManager platformTransactionManager = new JpaTransactionManager(factoryBean.getObject());
//        dataSources.add(platformTransactionManager);
    }
}
