package cn.night.fuo.web;

import cn.night.fuo.FuoEnvironment;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.web.cors.CorsEnvironment;
import cn.night.fuo.web.log.WebLogEnvironment;
import cn.night.fuo.web.mvc.WebMvcEnvironment;
import cn.night.fuo.web.serializer.WebSerializerEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class WebStarter {

    @Bean(value = "cn.night.fuo.web.cors.CorsEnvironment")
    public CorsEnvironment buildCorsEnv() throws FuoEnvironmentInitializeFailedException {
        CorsEnvironment environment = new CorsEnvironment();
        environment.build();
        return environment;
    }

    @Bean(value = "cn.night.fuo.web.log.WebLogEnvironment")
    public WebLogEnvironment buildWebLogEnv() throws FuoEnvironmentInitializeFailedException {
        WebLogEnvironment environment = new WebLogEnvironment();
        environment.build();
        return environment;
    }

    @Bean(value = "cn.night.fuo.web.serializer.WebSerializerEnvironment")
    public WebSerializerEnvironment buildWebSerializerEnv() throws FuoEnvironmentInitializeFailedException {
        WebSerializerEnvironment environment = new WebSerializerEnvironment();
        environment.build();
        return environment;
    }

    @Bean(value = "cn.night.fuo.web.mvc.WebMvcEnvironment")
    public WebMvcEnvironment buildWebMvcEnv() throws FuoEnvironmentInitializeFailedException {
        WebMvcEnvironment environment = new WebMvcEnvironment();
        environment.build();
        return environment;
    }
}
