package cn.night.fuo.serializer.json.serializer.format.date;

import cn.night.fuo.serializer.json.serializer.IFuoJsonSerializerFormat;
import cn.night.fuo.utils.Utils;
import com.alibaba.fastjson.serializer.JSONSerializer;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatSerializer_yyyyMMddHHmmss implements IFuoJsonSerializerFormat {

    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if(object == null) {
            serializer.out.writeNull();
        } else {
            Date date = (Date)object;
            String text = Utils.type.date.formatToString(date,yyyyMMddHHmmss);
            serializer.write(text);
        }
    }
}
