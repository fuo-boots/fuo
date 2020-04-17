package cn.night.fuo.persistent.mybatis.routing.interceptor;

import cn.night.fuo.persistent.mybatis.routing.swicher.DataSourceSwitcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class DynamicDataSourceRoutingPointCut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        DataSourceSwitcher switcher = method.getAnnotation(DataSourceSwitcher.class);
        return switcher != null && StringUtils.isNotBlank(switcher.group());
    }
}
