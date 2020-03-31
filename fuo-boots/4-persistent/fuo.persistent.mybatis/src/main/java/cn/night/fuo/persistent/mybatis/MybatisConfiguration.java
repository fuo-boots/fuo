package cn.night.fuo.persistent.mybatis;

import cn.night.fuo.persistent.mybatis.factories.object.MybatisObjectFactory;
import cn.night.fuo.persistent.mybatis.factories.object.MybatisObjectWrapperFactory;
import cn.night.fuo.persistent.mybatis.factories.reflector.MybatisReflectorFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;

public class MybatisConfiguration extends Configuration {
    private final MapperRegistry mapperRegistry;


    public MybatisConfiguration() {
        super();
        this.mapperRegistry = new MybatisMapperRegistry(this);
        this.setReflectorFactory(new MybatisReflectorFactory());
        this.setObjectFactory(new MybatisObjectFactory());
        this.setObjectWrapperFactory(new MybatisObjectWrapperFactory());
    }
}
