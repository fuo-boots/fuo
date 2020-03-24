package cn.night.fuo.web.mvc;

import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "fuo.web.mvc", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Data
public class MvcConf implements IConf {
    private List<InterceptorConf> interceptors = new ArrayList<>();

    @Override
    public void inspecting() {
        Assert.notNull(this.interceptors,"web.mvc.interceptors can not be null");
    }
}
