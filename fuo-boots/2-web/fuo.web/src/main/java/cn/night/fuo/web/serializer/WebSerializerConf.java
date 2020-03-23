package cn.night.fuo.web.serializer;

import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fuo.web.serializer", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Getter
public class WebSerializerConf implements IConf {
    private Boolean useFuoJsonConfig = true;

    @Override
    public void inspecting(){
        Assert.notNull(this.useFuoJsonConfig,"web.useFuoJsonConfig can not be null");
    }
}
