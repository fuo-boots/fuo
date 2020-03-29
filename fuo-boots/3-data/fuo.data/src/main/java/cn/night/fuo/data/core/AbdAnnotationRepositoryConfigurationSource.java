package cn.night.fuo.data.core;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.util.Streamable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AbdAnnotationRepositoryConfigurationSource extends AnnotationRepositoryConfigurationSource {
    private static final String BASE_PACKAGES = "basePackages";
    private static final String BASE_PACKAGE_CLASSES = "basePackageClasses";

    private final AnnotationMetadata configMetadata;
    private final AnnotationAttributes attributes;
    private final Environment environment;

    AbdAnnotationRepositoryConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation,
                                               ResourceLoader resourceLoader, Environment environment,BeanDefinitionRegistry registry) {
        super(metadata, annotation, resourceLoader, environment,registry);

        this.attributes = new AnnotationAttributes(metadata.getAnnotationAttributes(annotation.getName()));
        this.configMetadata = metadata;
        this.environment = environment;
    }



    @Override
    public Streamable<String> getBasePackages() {
//        Set<String> packages = new HashSet<>();
//        packages.add("cn.night.project");
//        return Streamable.of(packages);


        String[] value = attributes.getStringArray("value");
        String[] basePackages = attributes.getStringArray(BASE_PACKAGES);
        Class<?>[] basePackageClasses = attributes.getClassArray(BASE_PACKAGE_CLASSES);

        // Default configuration - return package of annotated class
        if (value.length == 0 && basePackages.length == 0 && basePackageClasses.length == 0) {

            String className = configMetadata.getClassName();
            return Streamable.of(ClassUtils.getPackageName(className));
        }

        Set<String> packages = new HashSet<>();
        packages.addAll(Arrays.asList(value));
        packages.addAll(Arrays.asList(basePackages));

        packages.add("cn.night.project.fuo.quickstart.jdbc.slave");

        Arrays.stream(basePackageClasses)//
                .map(ClassUtils::getPackageName)//
                .forEach(it -> packages.add(it));

        return Streamable.of(packages);




//        String value = attributes.getStringArray("value")[0];
//        String basePackages = attributes.getStringArray(BASE_PACKAGES)[0];
//        Class<?>[] basePackageClasses = attributes.getClassArray(BASE_PACKAGE_CLASSES);
//
//        // Default configuration - return package of annotated class
//        if (StringUtils.isEmpty(value) && StringUtils.isEmpty(basePackages) && basePackageClasses.length == 0) {
//            String className = configMetadata.getClassName();
//            return Collections.singleton(ClassUtils.getPackageName(className));
//        }
//
//        String[] packagesFromValue = parsePackagesSpel(value);
//        String[] packagesFromBasePackages = parsePackagesSpel(basePackages);
//
//        Set<String> packages = new HashSet<>();
//        packages.addAll(Arrays.asList(packagesFromValue));
//        packages.addAll(Arrays.asList(packagesFromBasePackages));
//
//        for (Class<?> typeName : basePackageClasses) {
//            packages.add(ClassUtils.getPackageName(typeName));
//        }
//
//        return packages;
    }

    private String[] parsePackagesSpel(String raw) {
        if (!raw.trim().startsWith("$")) {
            if (StringUtils.isEmpty(raw)) {
                return new String[]{};
            }
            return raw.split(",");
        } else {
            raw = raw.trim();
            String packages = this.environment.getProperty(raw.substring("${".length(), raw.length() - "}".length()));
            return packages.split(",");
        }
    }
}
