package cn.night.fuo.persistent.mybatis.processor;

import cn.night.fuo.persistent.mybatis.MybatisConfiguration;
import cn.night.fuo.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class MybatisBeanPostProcessor
        extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    private Configuration generateDefaultConfiguration() {
        Configuration configuration = new MybatisConfiguration();
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setUseColumnLabel(true);
        configuration.setUseGeneratedKeys(false);
        configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        configuration.setDefaultStatementTimeout(5000 / 1000);
        configuration.setSafeRowBoundsEnabled(false);
        configuration.setMapUnderscoreToCamelCase(false);
        configuration.setLocalCacheScope(LocalCacheScope.SESSION);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        configuration.addInterceptor(new MybatisValidInterceptor(properties));
//        configuration.addInterceptor(new MyBatisTraceInterceptor(applicationEventPublisher));
//        configuration.addInterceptor(new MybatisPageInterceptor());
//        configuration.getTypeHandlerRegistry().register(EnumValue.class, new EnumValueTypeHandler<>(EnumValue.class));
//        configuration.getTypeHandlerRegistry().register(CommonEntity.class, new JsonValueTypeHandler<>(CommonEntity.class));
//        configuration.getTypeHandlerRegistry().register(List.class, new JsonValueTypeHandler<>(List.class));
//        configuration.getTypeHandlerRegistry().register(Set.class, new JsonValueTypeHandler<>(Set.class));
//        configuration.getTypeHandlerRegistry().register(Map.class, new JsonValueTypeHandler<>(Map.class));
        return configuration;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }



    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof SqlSessionFactoryBean) {
            SqlSessionFactoryBean sqlSessionFactoryBean = (SqlSessionFactoryBean) bean;
            sqlSessionFactoryBean.setConfigLocation(null); //remove mybatis-config.xml
            sqlSessionFactoryBean.setConfiguration(generateDefaultConfiguration());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //extra converter could be added
        if (bean instanceof SqlSessionFactoryBean) {
            SqlSessionFactoryBean sqlSessionFactoryBean = (SqlSessionFactoryBean) bean;
            try {
                SqlSessionFactory sqlSessionFactoryObject = sqlSessionFactoryBean.getObject();
                if (Objects.nonNull(sqlSessionFactoryObject)) {
                    convertMappedStatement(sqlSessionFactoryObject);
                }
            } catch (Exception e) {
                log.error("Initialize mybatis post processor failed", e);
                throw new BeanInstantiationException(bean.getClass(), e.getMessage(), e);
            }
        }
        return bean;
    }

    private void convertMappedStatement(SqlSessionFactory sqlSessionFactory) {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        if (configuration == null || CollectionUtils.isEmpty(configuration.getMappedStatements())) {
            return;
        }
        for (Object mappedStatement : configuration.getMappedStatements()) {
            if (!(mappedStatement instanceof MappedStatement) || CollectionUtils.isEmpty(((MappedStatement) mappedStatement).getResultMaps())) {
                continue;
            }
            ((MappedStatement) mappedStatement).getResultMaps().stream()
                    .filter(resultMap -> resultMap != null && CollectionUtils.isEmpty(resultMap.getResultMappings()))
                    .flatMap(resultMap -> resultMap.getResultMappings().stream().map(mapping -> new ResultMapBinding(resultMap, mapping)))
                    .forEach(this::filterResultMapping);
        }
    }

    private void filterResultMapping(ResultMapBinding binding) {
//        String propertyName = binding.getResultMapping().getProperty();
//        Field propertyField = Utils.clazz.getPropertyField(propertyName, binding.getResultMap().getType());
//        if (propertyField == null
//                || !(binding.getResultMapping().getTypeHandler() instanceof JsonValueTypeHandler)
//                || !(binding.getResultMapping().getTypeHandler() instanceof EnumValueTypeHandler)) {
//            return;
//        }
//        Class<?> propertyClass = propertyField.getType();
//        if (CommonEntity.class.isAssignableFrom(propertyClass)
//                || List.class.isAssignableFrom(propertyClass)
//                || Set.class.isAssignableFrom(propertyClass)
//                || Map.class.isAssignableFrom(propertyClass)) {
//            ClassUtil.setPropertyValue(binding.getResultMapping(), TYPE_HANDLER, new JsonValueTypeHandler(propertyField.getGenericType()));
//        }
//        if (EnumValue.class.isAssignableFrom(propertyClass)) {
//            ClassUtil.setPropertyValue(binding.getResultMapping(), TYPE_HANDLER, new EnumValueTypeHandler(propertyField.getType()));
//        }
    }



    @Data
    @AllArgsConstructor
    private static class ResultMapBinding {

        private ResultMap resultMap;

        private ResultMapping resultMapping;
    }
}
