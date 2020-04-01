package cn.night.fuo.persistent.mybatis.factories.mapper;

import cn.night.fuo.persistent.common.data.PagedData;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MybatisMapperProxy implements InvocationHandler {
    private final Proxy mapperProxy;

    public MybatisMapperProxy(Proxy mapperProxy) {
        this.mapperProxy = mapperProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(mapperProxy, args);
        if (result instanceof PagedData) {
            return ((PagedData) result);//.getPage();
        }
        return result;
    }
}
