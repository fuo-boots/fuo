package cn.night.fuo.persistent.mybatis.factories.object;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.util.List;

public class MybatisObjectFactory implements ObjectFactory {

    private final ObjectFactory objectFactory = new DefaultObjectFactory();

    @Override
    public <T> T create(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T create(Class<T> aClass, List<Class<?>> list, List<Object> list1) {
        return null;
    }

    @Override
    public <T> boolean isCollection(Class<T> aClass) {
        return false;
    }
}
