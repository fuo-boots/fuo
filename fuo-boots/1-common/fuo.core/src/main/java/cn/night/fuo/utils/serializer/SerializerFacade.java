package cn.night.fuo.utils.serializer;

import cn.night.fuo.serializer.json.JsonSerializer;
import cn.night.fuo.spring.SpringContextHolder;

public class SerializerFacade {
    public final JsonSerializer json = SpringContextHolder.getBean(JsonSerializer.class);
}
