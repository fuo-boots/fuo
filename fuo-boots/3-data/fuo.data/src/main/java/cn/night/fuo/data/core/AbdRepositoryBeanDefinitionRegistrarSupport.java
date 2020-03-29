package cn.night.fuo.data.core;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.*;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

public abstract class AbdRepositoryBeanDefinitionRegistrarSupport extends RepositoryBeanDefinitionRegistrarSupport
        implements ImportBeanDefinitionRegistrar,ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");
        Assert.notNull(this.resourceLoader, "ResourceLoader must not be null!");
        String a = this.getAnnotation().getName();

        if (annotationMetadata.getAnnotationAttributes(this.getAnnotation().getName()) != null) {



            //重构此处
            AnnotationRepositoryConfigurationSource configurationSource = new AnnotationRepositoryConfigurationSource(
                    annotationMetadata,this.getAnnotation(), this.resourceLoader, this.environment, registry);

//            AbdAnnotationRepositoryConfigurationSource configurationSource = new AbdAnnotationRepositoryConfigurationSource(
//                    annotationMetadata, getAnnotation(), resourceLoader, environment,registry);

            RepositoryConfigurationExtension extension = this.getExtension();
            RepositoryConfigurationUtils.exposeRegistration(extension, registry, configurationSource);
            RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(configurationSource,
                    this.resourceLoader, this.environment);
            delegate.registerRepositoriesIn(registry, extension);
        }


        // 使用自定义的AbdAnnotationRepositoryConfigurationSource
//        AbdAnnotationRepositoryConfigurationSource configurationSource = new AbdAnnotationRepositoryConfigurationSource(
//                annotationMetadata, getAnnotation(), resourceLoader, environment);
//
//        RepositoryConfigurationExtension extension = getExtension();
//        RepositoryConfigurationUtils.exposeRegistration(extension, registry, configurationSource);
//
//        RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(configurationSource, resourceLoader,
//                environment);
//
//        delegate.registerRepositoriesIn(registry, extension);
    }
}
