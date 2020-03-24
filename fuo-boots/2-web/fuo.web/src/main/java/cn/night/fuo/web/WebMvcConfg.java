package cn.night.fuo.web;

import cn.night.fuo.FuoEnvironment;
import cn.night.fuo.serializer.SerializerEnvironment;
import cn.night.fuo.spring.SpringContextHolder;
import cn.night.fuo.web.mvc.InterceptorConf;
import cn.night.fuo.web.mvc.MvcConf;
import cn.night.fuo.web.serializer.WebSerializerEnvironment;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfg implements WebMvcConfigurer {

    @Autowired
    private WebConf conf;

    @Autowired
    private WebSerializerEnvironment webSerializerEnvironment;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (webSerializerEnvironment.getUseFuoJsonConfig()) {
            log.debug("web-mvc use fuo-serializer begin");
            FastJsonHttpMessageConverter fjc = new FastJsonHttpMessageConverter();
            FastJsonConfig fj = new FastJsonConfig();
            fj.setSerializerFeatures(webSerializerEnvironment.getJsonSerializeFeatures());
            fj.setSerializeConfig(webSerializerEnvironment.getJsonSerializeConfig());
            fjc.setFastJsonConfig(fj);
            converters.add(fjc);
            log.debug("web-mvc use fuo-serializer success");
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        MvcConf mvcConf = conf.getMvc();

        for (InterceptorConf interceptorConf : mvcConf.getInterceptors()) {
            log.debug(interceptorConf.getName() + " register begin");
            HandlerInterceptor interceptor = SpringContextHolder.getBeanByClazz(interceptorConf.getInterceptor());
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);
            interceptorRegistration.addPathPatterns(interceptorConf.getPath());
            interceptorRegistration.excludePathPatterns(interceptorConf.getExcludes());
            log.debug(interceptorConf.getName() + " registered success");
        }
    }
}
