package cn.night.fuo.pattern;

import cn.night.fuo.exception.runtime.FuoRuntimeException;
import cn.night.fuo.pattern.using.UsingInstanceConsumer;
import cn.night.fuo.pattern.using.UsingInstanceFactory;
import cn.night.fuo.pattern.using.UsingInstanceProcess;

public class Pattern {
    public static <T extends AutoCloseable, R> R using(UsingInstanceFactory<T> factory, UsingInstanceProcess<T, R> process) throws Exception {
        T context = null;
        try {
            context = factory.create();
            return process.process(context);
        } catch (Exception e) {
            throw e;
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (Exception e) {

                }
            }
        }
    }
    public static <T extends AutoCloseable> void using(UsingInstanceFactory<T> factory, UsingInstanceConsumer<T> process) throws FuoRuntimeException {
        T context = null;
        try {
            context = factory.create();
            process.consume(context);
        } catch (Exception e) {
            throw new FuoRuntimeException(e.getMessage(),e);
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (Exception e) {

                }
            }
        }
    }
}
