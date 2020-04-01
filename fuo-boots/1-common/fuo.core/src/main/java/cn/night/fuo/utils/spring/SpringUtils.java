package cn.night.fuo.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public final class SpringUtils {
    private static final String BASE_PACKAGE = "basePackages";

    private static final String BASE_PACKAGE_CLASS = "basePackageClasses";

    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

    public static List<String> getBasePackages(BeanDefinitionRegistry registry) {
        return Arrays.stream(registry.getBeanDefinitionNames())
                .flatMap(beanName -> convertConfigurationPackages(beanName, registry))
                .distinct()
                .collect(Collectors.toList());
    }

    public static Resource[] getResource(String location) {
        try {
            return RESOURCE_PATTERN_RESOLVER.getResources(location.trim());
        } catch (Exception e) {
            log.error("Mapper resource convert failed, location: {}", location);
            throw new RuntimeException("Mapper resource convert failed");
        }
    }

    private static Stream<String> convertConfigurationPackages(String beanName, BeanDefinitionRegistry registry) {
        List<String> result = new LinkedList<>();
        BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
        String className = beanDefinition.getBeanClassName();
        if (StringUtils.isBlank(className) || beanDefinition.getFactoryMethodName() != null || !(beanDefinition instanceof AnnotatedBeanDefinition)) {
            return result.stream();
        }
        if (!StringUtils.equals(className, ((AnnotatedBeanDefinition) beanDefinition).getMetadata().getClassName())) {
            return result.stream();
        }
        AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ComponentScan.class.getName()));
        if (attributes == null) {
            return result.stream();
        }
        String[] basePackagesArray = attributes.getStringArray(BASE_PACKAGE);
        Class[] basePackageClassArray = attributes.getClassArray(BASE_PACKAGE_CLASS);
        result.addAll(Arrays.asList(basePackagesArray));
        result.addAll(Arrays.stream(basePackageClassArray).map(ClassUtils::getPackageName).collect(Collectors.toList()));
        return result.stream();
    }
}
