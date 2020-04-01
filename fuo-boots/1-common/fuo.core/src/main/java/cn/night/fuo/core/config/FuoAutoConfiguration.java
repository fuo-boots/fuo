package cn.night.fuo.core.config;

import cn.night.fuo.core.config.configurer.FuoAutoConfigurer;
import jdk.nashorn.internal.ir.annotations.Reference;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FuoAutoConfiguration {
    String PROPERTIES = "properties";

    String CONFIGURER = "configurer";

    Class<? extends FuoAutoConfigurer> configurer();

    Class<?> properties();
}
