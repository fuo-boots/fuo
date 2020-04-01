package cn.night.fuo.core.config.registrar;

import cn.night.fuo.core.config.FuoAutoConfiguration;
import cn.night.fuo.core.config.configurer.FuoAutoConfigurer;
import cn.night.fuo.core.config.constants.ConfigConstants;
import cn.night.fuo.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

@Slf4j
public class FuoAutoConfigurationRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        ConfigurationPropertiesBindingPostProcessor.register(beanDefinitionRegistry);

        Object properties = annotationMetadata.getAnnotations().stream(FuoAutoConfiguration.class)
                .map(annotation -> this.generateProperties(annotation, beanDefinitionRegistry))
                .findFirst().orElseThrow(()->new IllegalArgumentException("ConfigurationProperties Is NULL"));

        annotationMetadata.getAnnotations().stream(FuoAutoConfiguration.class)
                .map(this::generateConfigurer)
                .forEach(configurer -> this.triggerConfiguration(properties, configurer, beanDefinitionRegistry));
    }

    private FuoAutoConfigurer generateConfigurer(MergedAnnotation annotation) {
        if (!annotation.isPresent()) {
            return null;
        }
        Class configurerClass = annotation.getClass(FuoAutoConfiguration.CONFIGURER);
        return (FuoAutoConfigurer) Utils.clazz.createInstance(configurerClass);
    }

    private Object generateProperties(MergedAnnotation annotation, BeanDefinitionRegistry registry) {
        if (!annotation.isPresent()) {
            return null;
        }
        Class propertiesClass = annotation.getClass(FuoAutoConfiguration.PROPERTIES);
        MergedAnnotation propertiesMergedAnnotation = MergedAnnotations.from(propertiesClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ConfigurationProperties.class);
        if (!propertiesMergedAnnotation.isPresent()) {
            return null;
        }
        String prefix = propertiesMergedAnnotation.getString(ConfigConstants.PREFIX);
        String beanName = StringUtils.isNotBlank(prefix) ? prefix + ConfigConstants.SYMBOL_HYPHEN + propertiesClass.getName(): propertiesClass.getName();

        String[] propertyNames = ((ConfigurableListableBeanFactory) beanFactory).getBeanNamesForType(PropertySourcesPlaceholderConfigurer.class);
        if (ArrayUtils.isEmpty(propertyNames)) {
            registry.registerBeanDefinition(PropertySourcesPlaceholderConfigurer.class.getName(), this.createPropertySource());
        } else {
            Arrays.stream(propertyNames).forEach(registry::removeBeanDefinition);
            Arrays.stream(propertyNames).forEach(name -> registry.registerBeanDefinition(name, this.createPropertySource()));
        }
        PropertySourcesPlaceholderConfigurer configurer = beanFactory.getBean(PropertySourcesPlaceholderConfigurer.class);
        configurer.postProcessBeanFactory((ConfigurableListableBeanFactory) beanFactory);
        ConfigurationPropertiesBindingPostProcessor processor = beanFactory.getBean(ConfigurationPropertiesBindingPostProcessor.class);
        return processor.postProcessBeforeInitialization(Utils.clazz.createInstance(propertiesClass), beanName);
    }

    private BeanDefinition createPropertySource() {
        return BeanDefinitionBuilder.genericBeanDefinition(PropertySourcesPlaceholderConfigurer.class)
                .addPropertyValue("locations", Utils.spring.getResource("classpath*:**/*.properties"))
                .getBeanDefinition();
    }

    private void triggerConfiguration(Object properties, FuoAutoConfigurer configurer, BeanDefinitionRegistry registry){
        if (configurer == null) {
            log.error("FuoAutoConfigurationRegistrar, configurer can not be null");
            throw new IllegalArgumentException("FuoAutoConfigurationRegistrar, configurer can not be null");
        }
        try {
            configurer.init(properties, registry);
            configurer.configurer(properties, registry);
        } catch (Exception e) {
            log.error("FuoAutoConfigurationRegistrar, configurer process failed", e);
            throw new IllegalArgumentException(e);
        }
    }
}
