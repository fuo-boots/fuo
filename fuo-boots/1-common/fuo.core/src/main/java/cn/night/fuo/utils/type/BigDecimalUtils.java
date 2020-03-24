package cn.night.fuo.utils.type;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public String roundDownToString(BigDecimal value,int scale){
        return value.setScale(scale,BigDecimal.ROUND_DOWN).toString();
    }
}
