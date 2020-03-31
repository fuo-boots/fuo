package cn.night.fuo;

import cn.night.fuo.core.env.FuoEnvironmentBuilder;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.serializer.SerializerEnvironment;
import lombok.Getter;
import org.springframework.stereotype.Component;

//@Component(value = "cn.night.fuo.FuoEnvironment")
@Getter
public class FuoEnvironment implements FuoEnvironmentBuilder {

    private SerializerEnvironment serializerEnvironment = new SerializerEnvironment();

    @Override
    public void doBuild() throws FuoEnvironmentInitializeFailedException {
        this.serializerEnvironment.build();
    }
}
