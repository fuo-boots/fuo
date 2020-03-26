package cn.night.project.fuo.quickstart;

import cn.night.fuo.spring.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class})
//@EnableTransactionManagement

@SpringBootApplication
@ComponentScan(basePackages = {"cn.night.*"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class,args);

        JdbcTestRepository repository = SpringContextHolder.getBean(JdbcTestRepository.class);

        TestEntity testCase = new TestEntity();
        testCase.setAge(1);
        testCase.setName("test");
        testCase.setCreator("testor");
        testCase.setModifier("testor");

        repository.save(testCase);

        System.out.println("123");
    }
}
