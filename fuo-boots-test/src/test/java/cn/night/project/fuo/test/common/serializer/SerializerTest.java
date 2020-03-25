package cn.night.project.fuo.test.common.serializer;

import cn.night.fuo.utils.Utils;
import javafx.application.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootApplication
@SpringBootTest(classes = {SerializerTest.class})
@ComponentScan(basePackages = {"cn.night.*"})
public class SerializerTest {

    @Before
    public void testBefore() {
        System.out.println("fuo.serializer test beginning");
    }

    @After
    public void testAfter() {
        System.out.println("fuo.serializer test success");
    }


    @Test
    public void serializerDate() {
        Date value = new Date(1584799196901L);
        String t = Utils.serializer.json.toJSONString(value);
        Assert.assertEquals(t, "\"2020-03-21 21:59:56\"");
    }

    @Test
    public void serializerBigDecimal() {
        BigDecimal value = new BigDecimal("126736.12671267672332736");
        String t = Utils.serializer.json.toJSONString(value);
        Assert.assertEquals(t, "\"126736.12\"");
    }
}
