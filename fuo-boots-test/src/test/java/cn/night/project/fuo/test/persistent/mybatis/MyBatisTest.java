package cn.night.project.fuo.test.persistent.mybatis;

//import cn.night.project.fuo.test.persistent.entities.TestEntity;
import cn.night.project.fuo.test.common.serializer.SerializerTest;
import cn.night.project.fuo.test.persistent.entities.TestEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = {"cn.night.*"})
@org.mybatis.spring.annotation.MapperScan("cn.night")
@SpringBootApplication
@SpringBootTest(classes = {MyBatisTest.class})
public class MyBatisTest {

    @Autowired
    private TestUserMapper mapper;

    @Test
    public void crud(){
//        TestEntity testCase = new TestEntity();
//        testCase.setAge(1);
//        testCase.setName("test");
//        testCase.setCreator("testor");
//        testCase.setModifier("testor");

//        mapper.save(testCase);

        mapper.findById(1L);
    }

    @Test
    public void t(){
        System.out.println("456");
    }
}
