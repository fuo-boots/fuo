package cn.night.fuo.pattern.using;

@FunctionalInterface
public interface UsingInstanceConsumer<T> {
    void consume(T context) throws Exception;
}
