package cn.night.fuo.pattern.using;

@FunctionalInterface
public interface UsingInstanceProcess<T,R> {
    R process(T context) throws Exception;
}
