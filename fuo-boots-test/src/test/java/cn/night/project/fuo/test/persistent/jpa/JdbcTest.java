//package cn.night.project.fuo.test.persistent.jpa;
//
//import cn.night.project.fuo.test.persistent.entities.TestEntity;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
////SpringJUnit4ClassRunner
////SpringRunner
//@RunWith(SpringJUnit4ClassRunner.class)
////@SpringBootApplication(exclude = {
////        DataSourceAutoConfiguration.class,
////        HibernateJpaAutoConfiguration.class,
////        DataSourceTransactionManagerAutoConfiguration.class})
////@ContextConfiguration(classes = Config.class)
////@SpringBootApplication
////@EnableTransactionManagement
//@SpringBootApplication
//@SpringBootTest(classes = {JdbcTest.class})
//@ComponentScan(basePackages = {"cn.night.*"})
////@EntityScan(basePackages = "cn.night.project")
////@EnableJpaRepositories(considerNestedRepositories = false,
////        basePackages = {"cn.night.project"})
//public class JdbcTest {
//    @Autowired
//    private JdbcTestRepository repository;
//
//    @Test
//    public void crud() {
//        TestEntity testCase = new TestEntity();
//        testCase.setAge(1);
//        testCase.setName("test");
//        testCase.setCreator("testor");
//        testCase.setModifier("testor");
//
//        repository.save(testCase);
//
//        TestEntity reFind = repository.findById(testCase.getId()).orElse(null);
//
//        Assert.assertEquals(testCase.getAge(),reFind.getAge());
//        Assert.assertEquals(testCase.getName(),reFind.getName());
//
//        System.out.printf("123");
//    }
//}
