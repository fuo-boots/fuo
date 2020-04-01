//package cn.night.fuo.persistent.mybatis_og;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.mapping.DatabaseIdProvider;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.mapper.ClassPathMapperScanner;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.type.AnnotationMetadata;
//import org.springframework.util.Assert;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//
//import javax.sql.DataSource;
//import java.util.List;
//
////@org.springframework.context.annotation.Configuration
////@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
////@ConditionalOnSingleCandidate(DataSource.class)
////@EnableConfigurationProperties(MybatisProperties.class)
////@AutoConfigureAfter(DataSourceAutoConfiguration.class)
//@ConditionalOnMissingBean(MybatisAutoConfiguration.class)
//@Slf4j
//public class MybatisAutoConfiguration implements InitializingBean {
//
//    @Autowired
//    @Qualifier("master")
//    private DataSource dataSource;
//
//    private final MybatisProperties properties;
//    private final Interceptor[] interceptors;
//    private final ResourceLoader resourceLoader;
//    private final DatabaseIdProvider databaseIdProvider;
//    private final List<ConfigurationCustomizer> configurationCustomizers;
//
//    public MybatisAutoConfiguration(MybatisProperties properties,
//                                    ObjectProvider<Interceptor[]> interceptorsProvider,
//                                    ResourceLoader resourceLoader,
//                                    ObjectProvider<DatabaseIdProvider> databaseIdProvider,
//                                    ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
//        this.properties = properties;
//        this.interceptors = interceptorsProvider.getIfAvailable();
//        this.resourceLoader = resourceLoader;
//        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
//        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        if (this.properties.isCheckConfigLocation()
//                && StringUtils.hasText(this.properties.getConfigLocation())) {
//            Resource resource = this.resourceLoader.getResource(this.properties.getConfigLocation());
//            Assert.state(resource.exists(), "Cannot find config location: " + resource
//                    + " (please add config file or check your Mybatis configuration)");
//        }
//    }
//
//    private void applyConfiguration(SqlSessionFactoryBean factory) {
//        Configuration configuration = this.properties.getConfiguration();
//        if (configuration == null && !StringUtils.hasText(this.properties.getConfigLocation())) {
//            configuration = new Configuration();
//        }
//        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
//            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
//                customizer.customize(configuration);
//            }
//        }
//        factory.setConfiguration(configuration);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        ExecutorType executorType = this.properties.getExecutorType();
//        if (executorType != null) {
//            return new SqlSessionTemplate(sqlSessionFactory, executorType);
//        } else {
//            return new SqlSessionTemplate(sqlSessionFactory);
//        }
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public SqlSessionFactory sqlSessionFactory() throws Exception { //DataSource dataSource
//        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setVfs(SpringBootVFS.class);
//
//        if (StringUtils.hasText(this.properties.getConfigLocation())) {
//            factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
//        }
//        applyConfiguration(factory);
//
//        if (this.properties.getConfigurationProperties() != null) {
//            factory.setConfigurationProperties(this.properties.getConfigurationProperties());
//        }
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            factory.setPlugins(this.interceptors);
//        }
//        if (this.databaseIdProvider != null) {
//            factory.setDatabaseIdProvider(this.databaseIdProvider);
//        }
//        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
//            factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
//        }
//        if (this.properties.getTypeAliasesSuperType() != null) {
//            factory.setTypeAliasesSuperType(this.properties.getTypeAliasesSuperType());
//        }
//        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
//            factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
//        }
//        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
//            factory.setMapperLocations(this.properties.resolveMapperLocations());
//        }
//        return factory.getObject();
//    }
//
//    public static class AutoConfiguredMapperScannerRegistrar
//            implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {
//
//        private BeanFactory beanFactory;
//
//        private ResourceLoader resourceLoader;
//
//        @Override
//        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//            this.beanFactory = beanFactory;
//        }
//
//        @Override
//        public void setResourceLoader(ResourceLoader resourceLoader) {
//            this.resourceLoader = resourceLoader;
//        }
//
//        @Override
//        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
//            if (!AutoConfigurationPackages.has(this.beanFactory)) {
//                log.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.");
//                return;
//            }
//            log.debug("Searching for mappers annotated with @Mapper");
//
//            List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
//            if (log.isDebugEnabled()) {
//                packages.forEach(pkg -> log.debug("Using auto-configuration base package '{}'", pkg));
//            }
//
//            ClassPathMapperScanner scanner = new ClassPathMapperScanner(beanDefinitionRegistry);
//            if (this.resourceLoader != null) {
//                scanner.setResourceLoader(this.resourceLoader);
//            }
//            scanner.setAnnotationClass(Mapper.class);
//            scanner.registerFilters();
//            scanner.doScan(StringUtils.toStringArray(packages));
//        }
//    }
//}
