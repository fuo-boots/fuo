package cn.night.fuo.web.mvc;

import cn.night.fuo.core.conf.IConf;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fuo.web.mvc", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Data
public class MvcConf implements IConf {
}
