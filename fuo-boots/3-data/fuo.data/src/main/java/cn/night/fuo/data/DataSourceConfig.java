package cn.night.fuo.data;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
    @Bean(value = "slave")
//    @Primary
    DataSource dataSourceSlave() {
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
                .url("jdbc:mysql://localhost:3306/fuo_test_slave")
                .username("root")
                .password("12abAB@1986")
                .build();

    }

    @Bean(value = "master")
//    @Primary
    public DataSource dataSourceMaster() {
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
                .password("12abAB@1986")
                .build();

    }

}
