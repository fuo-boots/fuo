package cn.night.fuo.persistent.mybatis.factories.reflector;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.Reflector;

public class MybatisReflectorFactory extends DefaultReflectorFactory {
    @Override
    public Reflector findForClass(Class<?> type) {
        return super.findForClass(type);
    }
}
