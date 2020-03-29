package cn.night.project.fuo.quickstart;

import cn.night.fuo.data.repository.BaseRepository;
import cn.night.fuo.spring.SpringContextHolder;
import cn.night.project.fuo.quickstart.jdbc.entities.TestEntity;
import cn.night.project.fuo.quickstart.jdbc.main.JdbcTestMainRepository;
import cn.night.project.fuo.quickstart.jdbc.slave.JdbcTestSlaveRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class})
//@EnableTransactionManagement

@SpringBootApplication
@ComponentScan(basePackages = {"cn.night.*"})
//@EntityScan(basePackages = "cn.night.project")
//@EntityScan(basePackageClasses = {TestEntity.class})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class,args);

//        BaseRepository<TestEntity,Long> baseRepository = SpringContextHolder.getBean(BaseRepository<TestEntity,Long>.class);

        JdbcTestMainRepository repository = SpringContextHolder.getBean(JdbcTestMainRepository.class);
//        JdbcTestSlaveRepository repositorySlave = SpringContextHolder.getBean(JdbcTestSlaveRepository.class);

        TestEntity testCase = new TestEntity();
        testCase.setAge(1);
        testCase.setName("test");
        testCase.setCreator("testor");
        testCase.setModifier("testor");

        repository.save(testCase);
//        baseRepository.save(testCase);
//        repositorySlave.save(testCase);

        System.out.println("123");
    }
}
