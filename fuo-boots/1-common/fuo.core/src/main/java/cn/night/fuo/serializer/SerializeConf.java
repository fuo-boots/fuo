package cn.night.fuo.serializer;

import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import cn.night.fuo.serializer.json.JsonConf;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fuo.serialize", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Data
public class SerializeConf implements IConf {
    private JsonConf json = new JsonConf();

    @Override
    public void inspecting(){
        Assert.notNull(this.json,"fuo.cn.night.fuo.project.test.core.serializer.json can not be null");
    }
}
