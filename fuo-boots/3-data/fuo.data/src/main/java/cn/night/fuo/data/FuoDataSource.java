//package cn.night.fuo.data;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.Properties;
//
//public class FuoDataSource {
////    @Bean
////    @ConfigurationProperties(prefix="datasource.man")
////    public DataSourceProperties manDataSourceProperties() { //2
////        return new DataSourceProperties();
////    }
//
//    @Bean
//    public DataSource manDataSource() { //3
//        DataSourceProperties manDataSourceProperties = new DataSourceProperties();
//        return DataSourceBuilder.create()
//                .driverClassName(manDataSourceProperties.getDriverClassName())
//                .url(manDataSourceProperties.getUrl())
//                .username(manDataSourceProperties.getUsername())
//                .password(manDataSourceProperties.getPassword())
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager manTransactionManager() { //4
//        EntityManagerFactory factory = manEntityManagerFactory().getObject();
//        return new JpaTransactionManager(factory);
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean manEntityManagerFactory() {//5
//        LocalContainerEntityManagerFactoryBean factory =
//                new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(manDataSource());
//        factory.setPackagesToScan("com.hjf.boot.demo.database.jpa");
//        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.hbm2ddl.auto", false);
//        jpaProperties.put("hibernate.show-sql", true);
//        factory.setJpaProperties(jpaProperties);
//        return factory;
//    }
//
//    @Bean
//    public DataSourceInitializer manDataSourceInitializer() {//6
//        DataSourceInitializer dsInitializer = new DataSourceInitializer();
//        dsInitializer.setDataSource(manDataSource());
//        ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator();
//        dbPopulator.addScript(new ClassPathResource("security-data.sql"));
//        dsInitializer.setDatabasePopulator(dbPopulator);
//        dsInitializer.setEnabled(false);
//        return dsInitializer;
//    }
//}
