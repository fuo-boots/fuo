package cn.night.fuo.persistent.mybatis.routing.swicher;

public class DataSourceSwitcherHolder {
    private static final ThreadLocal<String> HOLDER = new InheritableThreadLocal<>();

    public static String getLookupKey() {
        return HOLDER.get();
    }

    public static void setLookupKey(String lookupKey) {
        HOLDER.set(lookupKey);
    }

    public static void remove() {
        HOLDER.remove();
    }
}
