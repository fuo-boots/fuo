package cn.night.fuo.serializer.json.serializer;

import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FormatConf implements IConf {
    private String target;
    private String format;

    @Override
    public void inspecting() {
        Assert.notNull(this.target, "cn.night.fuo.project.test.core.serializer.json.formats.target can not be null");
        Assert.notNull(this.format,"cn.night.fuo.project.test.core.serializer.json.formats.format can not be null");
    }
}
