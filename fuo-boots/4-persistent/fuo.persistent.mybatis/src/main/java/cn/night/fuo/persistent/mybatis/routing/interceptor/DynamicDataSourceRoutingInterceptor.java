package cn.night.fuo.persistent.mybatis.routing.interceptor;

import cn.night.fuo.persistent.mybatis.routing.source.CommonDynamicDataSourceGroup;
import cn.night.fuo.persistent.mybatis.routing.swicher.DataSourceSwitcher;
import cn.night.fuo.persistent.mybatis.routing.swicher.DataSourceSwitcherHolder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Slf4j
@Setter
public class DynamicDataSourceRoutingInterceptor implements MethodInterceptor, Ordered {

    private BeanDefinition

    private CommonDynamicDataSourceGroup group;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        DataSourceSwitcher switcher = method.getAnnotation(DataSourceSwitcher.class);
        Transactional transactional = method.getAnnotation(Transactional.class);
        try {
            if (transactional != null || switcher != null && switcher.type() == DataSourceSwitcher.SwitcherType.MASTER) {
                DataSourceSwitcherHolder.setLookupKey(group.getMasterDataSource(switcher.group()));
            }
            if (switcher != null && switcher.type() == DataSourceSwitcher.SwitcherType.SLAVE && StringUtils.isBlank(DataSourceSwitcherHolder.getLookupKey())) {
                DataSourceSwitcherHolder.setLookupKey(group.getSlaveDataSource(switcher.group()));
            }
            return invocation.proceed();
        } catch (Exception e) {
            log.error("Database execution failed", e);
            throw new RuntimeException("Database execution failed");
        } finally {
            DataSourceSwitcherHolder.remove();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; //execute before TransactionInterceptor
    }
}
