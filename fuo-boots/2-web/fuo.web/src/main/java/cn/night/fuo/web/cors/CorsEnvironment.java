package cn.night.fuo.web.cors;

import cn.night.fuo.core.conf.ConfConstants;
import cn.night.fuo.core.env.FuoEnvironmentBuilder;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.spring.SpringContextHolder;
import cn.night.fuo.utils.Utils;
import cn.night.fuo.web.WebConf;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

@Component("cn.night.fuo.web.cors.CorsEnvironment")
@Getter
public class CorsEnvironment implements FuoEnvironmentBuilder {
    private WebConf conf = SpringContextHolder.getBean(WebConf.class);

    private Boolean enable = false;

    private HashSet<String> origins = new HashSet<>();

    private String allowMethod;

    private String maxAge;

    private String allowCredentials;

    private String allowHeaders;

    @Override
    public void doBuild() throws FuoEnvironmentInitializeFailedException {
        CORSConf cors = conf.getCors();
        this.enable = cors.getEnable();

        if (this.enable) {
            this.origins.forEach(x -> {
                if (!StringUtils.isEmpty(x)) {
                    this.origins.add(x.toLowerCase());
                }
            });
            this.allowMethod = Utils.collection.mkString(cors.getMethods(), ConfConstants.DEFAULT_SEP);
            this.maxAge = cors.getAge().toString();
            this.allowCredentials = cors.getCredentials().toString();
            this.allowHeaders = "Origin, X-Requested-With, Content-Type, Accept";
        }
    }
}
