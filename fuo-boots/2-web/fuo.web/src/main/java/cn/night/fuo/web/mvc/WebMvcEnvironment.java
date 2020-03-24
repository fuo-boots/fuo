package cn.night.fuo.web.mvc;

import cn.night.fuo.core.env.FuoEnvironmentBuilder;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.spring.SpringContextHolder;
import cn.night.fuo.web.WebConf;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.ArrayList;
import java.util.List;

@Component(value = "cn.night.fuo.web.mvc.WebMvcEnvironment")
@Slf4j
@Getter
public class WebMvcEnvironment implements FuoEnvironmentBuilder {
    private WebConf conf = SpringContextHolder.getBean(WebConf.class);
    private List<FuoWebHandlerInterceptor> handlerInterceptors = new ArrayList<>();

    @Override
    public void doBuild() throws FuoEnvironmentInitializeFailedException {
        log.debug(" Fuo-WebMvcEnvironment start");
        for (InterceptorConf interceptorConf : conf.getMvc().getInterceptors()) {
            log.debug(interceptorConf.getName() + " register begin");
            HandlerInterceptor interceptor = SpringContextHolder.getBeanByClazz(interceptorConf.getInterceptor());
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);
            interceptorRegistration.addPathPatterns(interceptorConf.getPath());
            interceptorRegistration.excludePathPatterns(interceptorConf.getExcludes());
            log.debug(interceptorConf.getName() + " registered success");
        }
        log.debug(" Fuo-WebMvcEnvironment success");
    }
}
