package cn.night.fuo.utils.type;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 日期工具类,统一使用 apache.common-lang3
 */
public class DateUtils {
    public String formatToString(Date date, String pattern) {
        return DateFormatUtils.format(date,pattern);
    }
}
