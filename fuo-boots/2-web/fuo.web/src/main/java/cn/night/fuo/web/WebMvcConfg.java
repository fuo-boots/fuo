package cn.night.fuo.web;

import cn.night.fuo.FuoEnvironment;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfg {
    @Autowired
    private WebConf conf;

    @Autowired
    private FuoEnvironment env;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if(conf.getSerializer().getUseFuoJsonConfig()) {
            FastJsonHttpMessageConverter fjc = new FastJsonHttpMessageConverter();
            FastJsonConfig fj = new FastJsonConfig();
            fj.setSerializerFeatures(env.getSerializerEnv().getJsonSerializeFeatures());
            fj.setSerializeConfig(env.getSerializerEnv().getJsonSerializeConfig());
            fjc.setFastJsonConfig(fj);
            converters.add(fjc);
        }
    }
}
