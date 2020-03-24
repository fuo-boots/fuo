package cn.night.fuo.web.serializer;

import cn.night.fuo.FuoEnvironment;
import cn.night.fuo.core.env.FuoEnvironmentBuilder;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.spring.SpringContextHolder;
import cn.night.fuo.web.WebConf;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "cn.night.fuo.web.serializer.WebSerializerEnvironment")
@Slf4j
@Getter
public class WebSerializerEnvironment implements FuoEnvironmentBuilder {
    private WebConf conf = SpringContextHolder.getBean(WebConf.class);
    private FuoEnvironment fuoEnvironment = SpringContextHolder.getBean(FuoEnvironment.class);

    private Boolean useFuoJsonConfig = true;
    private SerializeConfig jsonSerializeConfig;
    private SerializerFeature[] jsonSerializeFeatures;

    @Override
    public void doBuild() throws FuoEnvironmentInitializeFailedException {
        this.useFuoJsonConfig = conf.getSerializer().getUseFuoJsonConfig();
        if(this.useFuoJsonConfig){
            this.jsonSerializeConfig = fuoEnvironment.getSerializerEnvironment().getJsonSerializeConfig();
            this.jsonSerializeFeatures = fuoEnvironment.getSerializerEnvironment().getJsonSerializeFeatures();
        }
    }
}
