package cn.night.fuo.persistent.jpa.datasource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(value = "slave")
//    @Primary
    DataSource dataSourceSlave() {
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
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/fuo_test_main")
                .username("root")
                .password("12abAB@1986")
                .build();

    }
}
