package cn.night.fuo.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextHolder implements ApplicationContextInitializer {
    private static ApplicationContext applicationContext;
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
//applicationContext.getAutowireCapableBeanFactory().configureBean()
        assertApplicationContext();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBeanByClazz(String clazzName) {
        try {
            assertApplicationContext();
            return (T) applicationContext.getBean(Class.forName(clazzName));
        }catch (ClassNotFoundException e){
            return null;
        }
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext() {
        if (SpringContextHolder.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }
}
