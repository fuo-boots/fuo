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
            log.debug(interceptorConf.getName() + " build begin");
            HandlerInterceptor interceptor = SpringContextHolder.getBeanByClazz(interceptorConf.getInterceptor());
            if(interceptor == null){
                throw new FuoEnvironmentInitializeFailedException("Fuo-web-Mvc" + interceptorConf.getName() + " getBean is null" );
            }
            FuoWebHandlerInterceptor fuoWebHandlerInterceptor = new FuoWebHandlerInterceptor();
            fuoWebHandlerInterceptor.setExcludes(interceptorConf.getExcludes());
            fuoWebHandlerInterceptor.setPath(interceptorConf.getPath());
            fuoWebHandlerInterceptor.setInterceptor(interceptor);
            this.handlerInterceptors.add(fuoWebHandlerInterceptor);
            log.debug(interceptorConf.getName() + " build success");
        }
        log.debug(" Fuo-WebMvcEnvironment success");
    }
}
