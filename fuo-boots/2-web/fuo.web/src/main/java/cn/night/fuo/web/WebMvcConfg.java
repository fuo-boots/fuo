package cn.night.fuo.web;

import cn.night.fuo.FuoEnvironment;
import cn.night.fuo.serializer.SerializerEnvironment;
import cn.night.fuo.spring.SpringContextHolder;
import cn.night.fuo.web.mvc.InterceptorConf;
import cn.night.fuo.web.mvc.MvcConf;
import cn.night.fuo.web.mvc.WebMvcEnvironment;
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

    @Autowired
    private WebMvcEnvironment webMvcEnvironment;

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
        webMvcEnvironment.getHandlerInterceptors().forEach(interceptor->{
            log.debug(interceptor.getName() + " register begin");
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor.getInterceptor());
            interceptorRegistration.addPathPatterns(interceptor.getPath());
            interceptorRegistration.excludePathPatterns(interceptor.getExcludes());
            log.debug(interceptor.getName() + " register end");
        });
    }
}
