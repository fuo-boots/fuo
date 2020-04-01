package cn.night.fuo.persistent.mybatis.factories.mapper;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Proxy;

public class MybatisMapperRegistry extends MapperRegistry {
    public MybatisMapperRegistry(Configuration config) {
        super(config);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        T mapper = super.getMapper(type, sqlSession);
        if (mapper instanceof Proxy) {
            return (T) Proxy.newProxyInstance(mapper.getClass().getClassLoader(),
                    mapper.getClass().getInterfaces(),
                    new MybatisMapperProxy((Proxy) mapper));
        }
        return mapper;
    }
}
