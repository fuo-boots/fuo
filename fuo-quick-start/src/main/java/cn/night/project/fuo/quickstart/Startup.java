package cn.night.project.fuo.quickstart;

import cn.night.fuo.spring.SpringContextHolder;
import cn.night.project.fuo.quickstart.jdbc.main.JdbcTestMainRepository;
import cn.night.project.fuo.quickstart.jdbc.slave.JdbcTestSlaveRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class})
//@EnableTransactionManagement

@SpringBootApplication
@ComponentScan(basePackages = {"cn.night.*"})
//@EntityScan(basePackages = "cn.night.project")
@EntityScan(basePackageClasses = {TestEntity.class})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class,args);

        JdbcTestMainRepository repository = SpringContextHolder.getBean(JdbcTestMainRepository.class);

        TestEntity testCase = new TestEntity();
        testCase.setAge(1);
        testCase.setName("test");
        testCase.setCreator("testor");
        testCase.setModifier("testor");

        repository.save(testCase);

        System.out.println("123");
    }
}
