package cn.night.fuo.core.conf;

import cn.night.fuo.exception.runtime.FuoIllegalArgumenRuntimetException;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.util.Collection;

public interface IConf {
    default boolean inspectionAndInitializ() throws IllegalAccessException {
        this.initializing();

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (!fieldType.isPrimitive()) {
                if (IConf.class.isAssignableFrom(fieldType)) {
                    IConf fieldValue = (IConf) field.get(this);
                    if (fieldValue == null) {
                        throw new FuoIllegalArgumenRuntimetException(this.getClass() + ":" + field.getName()
                                + " inspectionAndInitializ error, fieldValue can not be null");
                    }
                    fieldValue.inspectionAndInitializ();
                } else if (Collection.class.isAssignableFrom(fieldType)) {
                    Field field2 = field;
                    ResolvableType resolvableType = ResolvableType.forField(field);
                    if (resolvableType != null) {
                        ResolvableType tType = resolvableType.getGeneric(0);
                        if (tType != null) {
                            Class<?> tt = tType.resolve();
                            if (IConf.class.isAssignableFrom(tt)) {
                                Collection<?> fieldValue = (Collection<?>) field.get(this);
                                if (fieldValue == null) {
                                    throw new FuoIllegalArgumenRuntimetException(this.getClass() + ":" + field.getName()
                                            + " inspectionAndInitializ error, fieldValue can not be null");
                                }
                                for (Object value : fieldValue) {
                                    IConf conf = (IConf) value;
                                    conf.inspectionAndInitializ();
                                }
                            }
                        }
                    }
                }
            }
        }

        this.inspecting();

        return true;
    }

    default void initializing() {
    }

    default void inspecting() {

    }
}
