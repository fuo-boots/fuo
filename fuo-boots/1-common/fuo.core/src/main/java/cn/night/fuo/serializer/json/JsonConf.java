package cn.night.fuo.serializer.json;

import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import cn.night.fuo.serializer.json.serializer.FormatConf;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "fuo.serialize.json", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Data
public class JsonConf implements IConf {
    //是否输出值为null的字段,默认为false
    private Boolean writeMapNullValue = false;
    // 字符类型字段如果为null,输出为"",而非null,默认为false
    private boolean writeNullStringAsEmpty =false;
    //数值字段如果为null,输出为0,而非null
    private boolean writeNullNumberAsZero = false;
    private List<FormatConf> formats = new ArrayList<>();

    @Override
    public void initializing() {

    }

    @Override
    public void inspecting() {
        Assert.notNull(this.writeMapNullValue,"cn.night.fuo.project.test.core.serializer.json.writeMapNullValue can not be null");
        Assert.notNull(this.writeNullStringAsEmpty,"cn.night.fuo.project.test.core.serializer.json.writeNullStringAsEmpty can not be null");
        Assert.notNull(this.writeNullNumberAsZero,"cn.night.fuo.project.test.core.serializer.json.writeNullNumberAsZero can not be null");
        Assert.notNull(this.formats,"cn.night.fuo.project.test.core.serializer.json.formats can not be null");
    }
}
