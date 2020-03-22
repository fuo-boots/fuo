package cn.night.fuo.serializer.json.serializer.format.date;

import cn.night.fuo.serializer.json.serializer.IFuoJsonSerializerFormat;
import com.alibaba.fastjson.serializer.JSONSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatSerializer implements IFuoJsonSerializerFormat {
    public DateFormatSerializer(){

    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if(object == null) {
            serializer.out.writeNull();
        } else {
            Date date = (Date)object;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String text = format.format(date);
            serializer.write(text);
        }
    }
}
