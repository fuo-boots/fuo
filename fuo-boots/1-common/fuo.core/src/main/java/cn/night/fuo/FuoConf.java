package cn.night.fuo;

import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.serializer.SerializeConf;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@ConfigurationProperties(prefix = "fuo", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Data
public class FuoConf implements IConf, InitializingBean {
    private SerializeConf serializer = new SerializeConf();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.inspectionAndInitializ();
    }

    @Override
    public void inspecting() {
        //Assert.notNull(this.serializer, "fuo.serializer can not be null");
    }
}
