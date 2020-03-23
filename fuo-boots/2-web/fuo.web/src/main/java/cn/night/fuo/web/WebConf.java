package cn.night.fuo.web;

import cn.night.fuo.core.conf.ConfConstants;
import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.web.cors.CORSConf;
import cn.night.fuo.web.log.WebLogConf;
import cn.night.fuo.web.mvc.MvcConf;
import cn.night.fuo.web.serializer.WebSerializerConf;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "fuo.web", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Getter
public class WebConf implements IConf, InitializingBean {
    private CORSConf cors = new CORSConf();
    private WebLogConf log = new WebLogConf();
    private WebSerializerConf serializer = new WebSerializerConf();
    private MvcConf mvc = new MvcConf();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.inspectionAndInitializ();
    }

    @Override
    public void inspecting() {
        Assert.notNull(this.serializer, "fuo.web.serializer can not be null");
        Assert.notNull(this.mvc, "fuo.web.mvc can not be null");
        Assert.notNull(this.cors, "fuo.web.cors can not be null");
        Assert.notNull(this.log, "fuo.web.log can not be null");
    }
}
