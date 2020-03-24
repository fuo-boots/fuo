package cn.night.fuo.serializer.json.serializer.format.decimal;

import cn.night.fuo.serializer.json.serializer.IFuoJsonSerializerFormat;
import cn.night.fuo.utils.Utils;
import com.alibaba.fastjson.serializer.JSONSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;

public class DecimalFormatSerializer_Down2 implements IFuoJsonSerializerFormat {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if(object == null) {
            serializer.out.writeNull();
        } else {
            BigDecimal value = (BigDecimal)object;
            String text = Utils.type.bigDecimal.roundDownToString(value,2);
            serializer.write(text);
        }
    }
}
