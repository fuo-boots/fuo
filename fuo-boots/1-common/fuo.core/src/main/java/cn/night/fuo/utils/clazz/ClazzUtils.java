package cn.night.fuo.utils.clazz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public final class ClazzUtils {
    public Boolean isPrimitiveType(Class clazz) {
        String name = clazz.getTypeName();
        return StringUtils.equals(name, Boolean.class.getName()) || StringUtils.equals(name, Character.class.getName())
                || StringUtils.equals(name, Byte.class.getName()) || StringUtils.equals(name, Short.class.getName())
                || StringUtils.equals(name, Integer.class.getName()) || StringUtils.equals(name, Long.class.getName())
                || StringUtils.equals(name, Float.class.getName()) || StringUtils.equals(name, Double.class.getName())
                || clazz.isPrimitive();
    }

    public Class<?>[] getTypeParameterClass(Object target) {
        Type type = target.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) type).getActualTypeArguments();
            return Arrays.stream(params).filter(param -> param instanceof Class).map(param -> (Class) param).toArray(Class[]::new);
        }
        return null;
    }

    public Class<?> getRawType(Type type) {
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getRawType();
        }
        return (Class) type;
    }

    public Class<?>[] getParamTypes(Type type) {
        if (type instanceof ParameterizedType) {
            return Arrays.stream(((ParameterizedType) type).getActualTypeArguments()).map(t -> (Class) t).toArray(Class[]::new);
        }
        return null;
    }

    public Field getPropertyField(String propertyName, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            Optional<Field> propertyField = Arrays.stream(fields).filter(field -> StringUtils.equals(propertyName, field.getName())).findFirst();
            return propertyField.orElse(null);
        }
        return null;
    }

    public void setPropertyValue(Object target, String propertyName, Object propertyValue) {
        Field field = getPropertyField(propertyName, target.getClass());
        if (field == null) {
            throw new RuntimeException("Field not found, propertyName: " + propertyName);
        }
        try {
            field.setAccessible(true);
            field.set(target, propertyValue);
        } catch (Exception e) {
            log.error("SetPropertyValue failed", e);
            throw new RuntimeException("Field not found, propertyName: " + propertyName, e);
        } finally {
            field.setAccessible(false);
        }
    }

    public <T> T getPropertyValue(Object target, String propertyName) {
        Field field = getPropertyField(propertyName, target.getClass());
        if (field == null) {
            throw new RuntimeException("Field not found, propertyName: " + propertyName);
        }
        try {
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (Exception e) {
            log.error("GetPropertyValue failed", e);
            throw new RuntimeException("Field not found, propertyName: " + propertyName, e);
        } finally {
            field.setAccessible(false);
        }
    }

    public <T> T createInstance(Class<T> clazz) {
        try {
            if (clazz.isArray()) {
                return (T) Array.newInstance(clazz.getComponentType(), 1);
            }
            if (clazz.isInterface()) {
                return (T) genInterfaceTypeObject(clazz);
            }
            if (isPrimitiveType(clazz)) {
                return (T) genPrimitiveTypeObject(clazz);
            }
            if (clazz.isEnum()) {
                return clazz.getEnumConstants()[0];
            }

            Constructor constructor = Arrays.stream(clazz.getConstructors()).min(Comparator.comparingInt(Constructor::getParameterCount)).orElse(null);
            if (constructor == null) {
                return null;
            }
            Type[] paramTypeArray = constructor.getGenericParameterTypes();
            if (paramTypeArray.length == 0) {
                return (T) constructor.newInstance();
            }
            return (T) constructor.newInstance(Arrays.stream(paramTypeArray).map(this::createInstance).collect(Collectors.toList()).toArray());
        } catch (Exception e) {
            log.error("createInstance failed", e);
        }
        return null;
    }

    private Object createInstance(Type type) {
        if (type instanceof ParameterizedType) {
            return createInstance(((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            return new Object();
        }
        return createInstance((Class) type);
    }

    private Object genInterfaceTypeObject(Class clazz) {
        if (List.class.isAssignableFrom(clazz)) {
            return new ArrayList<>();
        }
        if (Set.class.isAssignableFrom(clazz)) {
            return new HashSet<>();
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return new HashMap<>();
        }
        return null;
    }

    private Object genPrimitiveTypeObject(Class clazz) {
        if (char.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
            return '0';
        }
        if (boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (byte.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)) {
            return Byte.valueOf("0");
        }
        if (short.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz)) {
            return Short.valueOf("0");
        }
        if (int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)) {
            return Integer.valueOf("0");
        }
        if (long.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)) {
            return Long.valueOf("0");
        }
        if (float.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)) {
            return Float.valueOf("0.0");
        }
        if (double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)) {
            return Double.valueOf("0.0");
        }
        return null;
    }
}
