package cn.night.fuo.serializer.json;

import cn.night.fuo.FuoEnvironment;
import cn.night.fuo.serializer.SerializerEnvironment;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonSerializer  {
    @Autowired
    private FuoEnvironment env;

    public String toJSONString(Object object) {
        SerializerEnvironment serializerEnvironment = env.getSerializerEnvironment();
        return JSON.toJSONString(object, serializerEnvironment.getJsonSerializeConfig(),
                serializerEnvironment.getJsonSerializeFeatures());
    }

    public String toJSONOriginateString(Object object) {
        return JSON.toJSONString(object);
    }
}
