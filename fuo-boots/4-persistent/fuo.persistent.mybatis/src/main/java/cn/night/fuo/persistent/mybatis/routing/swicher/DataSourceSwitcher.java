package cn.night.fuo.persistent.mybatis.routing.swicher;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceSwitcher {

    String group();

    SwitcherType type();

    enum SwitcherType {

        MASTER,

        SLAVE,
    }
}

