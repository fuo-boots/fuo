package cn.night.fuo.utils.collection;

import cn.night.fuo.exception.runtime.FuoIllegalArgumenRuntimetException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class CollectionUtils {
    public <T> String mkString(Collection<T> list, String sep) {
        if (list == null) return null;
        if (StringUtils.isEmpty(sep)) {
            throw new FuoIllegalArgumenRuntimetException("CollectionUtils-mkString sep can not be empty");
        }
        StringBuilder builder = new StringBuilder(list.size() * 20);
        int idx = 0;
        list.forEach(item -> {
            if (item != null) {
                builder.append(item.toString());
                if (idx < list.size() - 1) {
                    builder.append(sep);
                }
            }
        });
        return builder.toString();
    }
}
