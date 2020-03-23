package cn.night.fuo.project.test.core.serializer;

import cn.night.fuo.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootApplication
@SpringBootTest(classes={JsonSerializerTest.class})
public class JsonSerializerTest {

    @Test
    public void serialDate(){
        Date value = new Date(1584944263061L);
    }
}
