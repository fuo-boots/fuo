package cn.night.fuo.pattern.asserts;

import cn.night.fuo.exception.runtime.FuoIllegalArgumenRuntimetException;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class Assert {
    public static <T> T notNull(@Nullable T value, String msg) {
        if (value == null) throw new FuoIllegalArgumenRuntimetException(msg);
        return value;
    }

    public static Class<?> classLoad(@Nullable String value,String msg) throws FuoIllegalArgumenRuntimetException{
        try {
            return Class.forName(value);
        }catch (ClassNotFoundException e){
            throw new FuoIllegalArgumenRuntimetException(msg);
        }
    }

    public static Class<?> classLoad(@Nullable String value) throws FuoIllegalArgumenRuntimetException{
        try {
            return Class.forName(value);
        }catch (ClassNotFoundException e){
            throw new FuoIllegalArgumenRuntimetException(e.getMessage());
        }
    }

    public static Object classCreateInstance(@Nullable String value) throws FuoIllegalArgumenRuntimetException{
        try {
            return Class.forName(value).newInstance();
        }catch (Exception e){
            throw new FuoIllegalArgumenRuntimetException(e.getMessage());
        }
    }

    @SneakyThrows
    public static void isTrue(boolean isSuccess, String msg) {
        if (!isSuccess) throw new FuoIllegalArgumenRuntimetException(msg);
        return;
    }

    @SneakyThrows
    public static String stringNotEmpty(@Nullable String value, String msg) {
        if (StringUtils.isEmpty(value)) {
            throw new FuoIllegalArgumenRuntimetException(msg);
        }
        return value;
    }

    @SneakyThrows
    public static Integer integerLimit(@Nullable Integer value, int min, int max, String msg) {
        if (value == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (value < min || value > max) {
            throw new FuoIllegalArgumenRuntimetException(msg);
        }
        return value;
    }

    @SneakyThrows
    public static boolean[] collectionNotEmpty(@Nullable boolean[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static String[] collectionNotEmpty(@Nullable String[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static byte[] collectionNotEmpty(@Nullable byte[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static char[] collectionNotEmpty(@Nullable char[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static double[] collectionNotEmpty(@Nullable double[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static float[] collectionNotEmpty(@Nullable float[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static int[] collectionNotEmpty(@Nullable int[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static long[] collectionNotEmpty(@Nullable long[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static short[] collectionNotEmpty(@Nullable short[] array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.length == 0) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }

    @SneakyThrows
    public static Collection<?> collectionNotEmpty(@Nullable Collection<?> array, String msg) {
        if (array == null) throw new FuoIllegalArgumenRuntimetException(msg);
        if (array.isEmpty()) throw new FuoIllegalArgumenRuntimetException(msg);
        return array;
    }
}
