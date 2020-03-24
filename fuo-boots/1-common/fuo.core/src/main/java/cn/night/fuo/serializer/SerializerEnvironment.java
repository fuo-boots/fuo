package cn.night.fuo.serializer;

import cn.night.fuo.core.env.FuoEnvironmentBuilder;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.pattern.asserts.Assert;
import cn.night.fuo.serializer.json.JsonConf;
import cn.night.fuo.serializer.json.serializer.FormatConf;
import cn.night.fuo.spring.SpringContextHolder;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SerializerEnvironment implements FuoEnvironmentBuilder {

    private SerializeConf serializeConf = SpringContextHolder.getBean(SerializeConf.class);

    private SerializeConfig jsonSerializeConfig;
    private SerializerFeature[] jsonSerializeFeatures;

    @Override
    public void doBuild() throws FuoEnvironmentInitializeFailedException {
        this.jsonBuild();
    }

    private void jsonBuild(){
        JsonConf jsonConf = serializeConf.getJson();

        SerializeConfig serializeConfig = new SerializeConfig();
        for (FormatConf formatConf: jsonConf.getFormats()) {
            Class<?> targetClazz = Assert.classLoad(formatConf.getTarget());
            Object formatInstance = Assert.classCreateInstance(formatConf.getFormat());
            serializeConfig.put(targetClazz,formatInstance);
        }
        this.jsonSerializeConfig = serializeConfig;

        List<SerializerFeature> features = new ArrayList<>();
        //强制使用:禁用fastjson
//        features.add(SerializerFeature.DisableCircularReferenceDetect);
        this.jsonSerializeFeatures = features.toArray(new SerializerFeature[features.size()]);
    }
}
