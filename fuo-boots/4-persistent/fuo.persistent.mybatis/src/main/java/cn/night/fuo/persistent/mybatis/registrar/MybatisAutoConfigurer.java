package cn.night.fuo.persistent.mybatis.registrar;

import cn.night.fuo.core.conf.ConfConstants;
import cn.night.fuo.core.config.configurer.FuoAutoConfigurer;
import cn.night.fuo.persistent.mybatis.MybatisProperties;
import cn.night.fuo.persistent.mybatis.routing.interceptor.DynamicDataSourceRoutingInterceptor;
import cn.night.fuo.persistent.mybatis.routing.interceptor.DynamicDataSourceRoutingPointCut;
import cn.night.fuo.persistent.mybatis.routing.source.CommonDynamicDataSource;
import cn.night.fuo.persistent.mybatis.routing.source.CommonDynamicDataSourceGroup;
import cn.night.fuo.utils.Utils;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class MybatisAutoConfigurer implements FuoAutoConfigurer<MybatisProperties> {

    @Override
    public void init(MybatisProperties properties, BeanDefinitionRegistry registry) throws Exception {
        if (properties.getConnectionGroup() == null) {
            log.error("Mybatis didn't have connection config, properties: {}", properties);
            throw new IllegalArgumentException("Mybatis didnt have connection config, properties");
        }
    }


    @Override
    public void configurer(MybatisProperties properties, BeanDefinitionRegistry registry) throws Exception {
        //MapperScan
        Pair<String, BeanDefinition> mapperScan = this.getMapperScan(registry);
        registry.registerBeanDefinition(mapperScan.getFirst(), mapperScan.getSecond());

        CommonDynamicDataSourceGroup dataSourceGroup = new CommonDynamicDataSourceGroup(properties);
        dataSourceGroup.init();
        Map<String, CommonDynamicDataSourceGroup.Group> groupMap = dataSourceGroup.getGroupMap();

        //DataSource
        Map<String, BeanDefinition> connectionMap = properties.getConnectionGroup().entrySet().stream()
                .flatMap(entry -> this.getConnection(entry.getKey(), entry.getValue(), groupMap))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, throwingMerger(), ManagedMap::new));
        String defaultConnectionName = groupMap.values().stream().filter(CommonDynamicDataSourceGroup.Group::getCheckDefault).map(CommonDynamicDataSourceGroup.Group::getMasterName).findFirst().orElseThrow(RuntimeException::new);
        BeanDefinition dynamicDataSource = BeanDefinitionBuilder.genericBeanDefinition(CommonDynamicDataSource.class)
                .addPropertyValue("targetDataSources", connectionMap)
                .addPropertyReference("defaultTargetDataSource", defaultConnectionName)
                .getBeanDefinition();
        registry.registerBeanDefinition(CommonDynamicDataSource.class.getName(), dynamicDataSource);
        connectionMap.forEach(registry::registerBeanDefinition);

        //SqlSessionFactoryBean
        BeanDefinition sqlSessionFactoryBean = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class)
                .addPropertyReference("dataSource", CommonDynamicDataSource.class.getName())
                .addPropertyValue("mapperLocations", StringUtils.isBlank(properties.getMapperLocation()) ? null : this.getMapperResources(properties.getMapperLocation()))
                .getBeanDefinition();
        registry.registerBeanDefinition(SqlSessionFactoryBean.class.getName(), sqlSessionFactoryBean);

        //SqlSessionTemplate
        BeanDefinition sqlSessionTemplate = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionTemplate.class)
                .addConstructorArgValue(sqlSessionFactoryBean)
                .getBeanDefinition();
        registry.registerBeanDefinition(SqlSessionTemplate.class.getName(), sqlSessionTemplate);

        //TransactionManager
        BeanDefinition transactionManager = BeanDefinitionBuilder.genericBeanDefinition(DataSourceTransactionManager.class)
                .addPropertyReference("dataSource", CommonDynamicDataSource.class.getName())
                .getBeanDefinition();
        registry.registerBeanDefinition(DataSourceTransactionManager.class.getName(), transactionManager);

        //Routing
        BeanDefinition routingInterceptor = BeanDefinitionBuilder.genericBeanDefinition(DynamicDataSourceRoutingInterceptor.class)
                .addPropertyValue("group", dataSourceGroup)
                .getBeanDefinition();
        BeanDefinition routingAdvisor = BeanDefinitionBuilder.genericBeanDefinition(DefaultPointcutAdvisor.class)
                .addPropertyValue("advice", routingInterceptor)
                .addPropertyValue("pointcut", new DynamicDataSourceRoutingPointCut())
                .getBeanDefinition();
        registry.registerBeanDefinition(DynamicDataSourceRoutingInterceptor.class.getName(), routingInterceptor);
        registry.registerBeanDefinition(DynamicDataSourceRoutingPointCut.class.getName(), routingAdvisor);
    }

    private Stream<Pair<String, BeanDefinition>> getConnection(String groupName, MybatisProperties.ConnectionGroup group, Map<String, CommonDynamicDataSourceGroup.Group> groupMap) {
        List<Pair<String, BeanDefinition>> resultList = new ArrayList<>();
        BeanDefinition master = this.getConnection(groupMap.get(groupName).getMasterName(), group.getMasterConnection());
        resultList.add(Pair.of(groupMap.get(groupName).getMasterName(), master));
        if (!CollectionUtils.isEmpty(group.getSlaveConnection())) {
            for (int i = 0; i < group.getSlaveConnection().size(); i++) {
                BeanDefinition salve = this.getConnection(groupMap.get(groupName).getSlaveNames().get(i), group.getSlaveConnection().get(i));
                resultList.add(Pair.of(groupMap.get(groupName).getSlaveNames().get(i), salve));
            }
        }
        return resultList.stream();
    }

    private BeanDefinition getConnection(String name, MybatisProperties.ConnectionConfig config) {
        Properties properties = new Properties();
        if (!StringUtils.contains(config.getUrl(), "useUnicode")) {
            properties.put("useUnicode", "true");
        }
        if (!StringUtils.contains(config.getUrl(), "characterEncoding")) {
            properties.put("characterEncoding", "utf8");
        }
        if (!StringUtils.contains(config.getUrl(), "serverTimezone")) {
            properties.put("serverTimezone", "GMT+8:00");
        }
        if (!StringUtils.contains(config.getUrl(), "allowMultiQueries")) {
            properties.put("allowMultiQueries", "true");
        }
        return BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class)
                .addPropertyValue("name", name)
                .addPropertyValue("driverClassName", config.getType().getDriverClassName())
                .addPropertyValue("url", config.getUrl())
                .addPropertyValue("username", config.getUserName())
                .addPropertyValue("password", config.getPassword())
                .addPropertyValue("initialSize", config.getMinConnection())
                .addPropertyValue("minIdle", config.getMinConnection())
                .addPropertyValue("maxActive", config.getMaxConnection())
                .addPropertyValue("maxWait", config.getConnectionTimeout() == null ? Integer.valueOf(60000) : config.getConnectionTimeout())
                .addPropertyValue("timeBetweenEvictionRunsMillis", 60000)
                .addPropertyValue("minEvictableIdleTimeMillis", 30000)
                .addPropertyValue("testOnBorrow", true)
                .addPropertyValue("testOnReturn", false)
                .addPropertyValue("validationQuery", "SELECT 1")
                .addPropertyValue("testWhileIdle", true)
                .addPropertyValue("keepAlive", true)
                .addPropertyValue("connectProperties", properties)
                .setInitMethodName("init")
                .setDestroyMethodName("close")
                .getBeanDefinition();
    }

    private Pair<String, BeanDefinition> getMapperScan(BeanDefinitionRegistry registry) {
        BeanDefinition mapperScanBean = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class)
                .addPropertyValue("processPropertyPlaceHolders", true)
                .addPropertyValue("annotationClass", Mapper.class)
                .addPropertyValue("basePackage",
                        org.springframework.util.StringUtils.collectionToCommaDelimitedString(Utils.spring.getBasePackages(registry)))
                .getBeanDefinition();
        return Pair.of(MapperScannerConfigurer.class.getName(), mapperScanBean);
    }

    private Resource[] getMapperResources(String mapperLocation) {
        return Arrays.stream(mapperLocation.split(ConfConstants.DEFAULT_SEP)).flatMap(location -> Stream.of(Utils.spring.getResource(location))).toArray(Resource[]::new);
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }
}
