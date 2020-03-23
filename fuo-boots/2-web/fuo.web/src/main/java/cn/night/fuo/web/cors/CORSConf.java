package cn.night.fuo.web.cors;

import cn.night.fuo.core.conf.ConfConstants;
import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "fuo.web.cors", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Data
public class CORSConf implements IConf {
    private Boolean enable = false;
    private List<String> origins = new ArrayList<>();
    private List<String> methods = new ArrayList<>();
    private Integer age = 36000;
    private Boolean credentials = false;

    @Override
    public void initializing(){
        if(this.origins.isEmpty()){
            this.origins.add(ConfConstants.ANY);
        }
        if(this.methods.isEmpty()){
            this.methods.add(ConfConstants.ANY);
        }
    }

    @Override
    public void inspecting(){
        Assert.notNull(this.enable,"config: fuo.web.cors.enable can not be null");
        if(this.enable){
            Assert.collectionNotEmpty(this.origins,"config: fuo.web.cors.enable = true,so fuo.web.cors.origins can not be empty");
            Assert.collectionNotEmpty(this.methods,"config: fuo.web.cors.enable = true,so fuo.web.cors.methods can not be empty");
            Assert.notNull(this.age,"config: fuo.web.cors.enable = true,so fuo.web.cors.age can not be null");
            Assert.integerLimit(this.age,0,72000,"config:fuo.web.cors.age must limit in [0,72000]");
            Assert.notNull(this.credentials,"config: fuo.web.cors.enable = true,so fuo.web.cors.credentials can not be null");
        }
    }
}
