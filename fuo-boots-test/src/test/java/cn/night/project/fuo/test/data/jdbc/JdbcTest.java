package cn.night.project.fuo.test.data.jdbc;

import cn.night.fuo.data.Config;
import cn.night.project.fuo.test.data.TestEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

//SpringJUnit4ClassRunner
@RunWith(SpringRunner.class)
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
//@ContextConfiguration(classes = Config.class)
//@SpringBootApplication
@EnableTransactionManagement
@SpringBootTest(classes = {JdbcTest.class})
@ComponentScan(basePackages = {"cn.night.*"})
@EntityScan(basePackages = "cn.night.project")
@EnableJpaRepositories(considerNestedRepositories = false,
        basePackages = {"cn.night.project"})
public class JdbcTest {
    @Autowired
    private JdbcTestRepository repository;

    @Test
    public void crud() {
        TestEntity testCase = new TestEntity();
        testCase.setAge(1);
        testCase.setName("test");
        testCase.setCreator("testor");
        testCase.setModifier("testor");

        repository.save(testCase);

        TestEntity reFind = repository.findById(testCase.getId()).orElse(null);

        Assert.assertEquals(testCase.getAge(),reFind.getAge());
        Assert.assertEquals(testCase.getName(),reFind.getName());

        System.out.printf("123");
    }
}
