package cn.night.fuo.pattern.using;

@FunctionalInterface
public interface UsingInstanceFactory<T> {
    T create() throws Exception;
}
