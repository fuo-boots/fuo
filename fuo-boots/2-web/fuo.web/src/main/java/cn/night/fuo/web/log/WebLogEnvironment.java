package cn.night.fuo.web.log;

import cn.night.fuo.core.conf.ConfConstants;
import cn.night.fuo.core.env.FuoEnvironmentBuilder;
import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;
import cn.night.fuo.spring.SpringContextHolder;
import cn.night.fuo.web.WebConf;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Component(value = "cn.night.fuo.web.log.WebLogEnvironment")
@Getter
public class WebLogEnvironment implements FuoEnvironmentBuilder {

    private WebConf conf = SpringContextHolder.getBean(WebConf.class);

    private Boolean enabled = true;

    private Boolean isMethodAllowAll = false;
    private HashSet<String> methodFilters = new HashSet<>();
    private HashSet<String> includeInfos = new HashSet<>();

    @Override
    public void doBuild() throws FuoEnvironmentInitializeFailedException {
        WebLogConf webLogConf = conf.getLog();

        this.enabled = webLogConf.getEnabled();

        if (this.enabled) {
            if (webLogConf.getMethodFilters().contains(ConfConstants.ANY)) {
                this.isMethodAllowAll = true;
            }
            webLogConf.getMethodFilters().forEach(x -> {
                if (!StringUtils.isEmpty(x)) {
                    this.methodFilters.add(x);
                }
            });
            webLogConf.getIncludeInfos().forEach(x -> {
                if (!StringUtils.isEmpty(x)) {
                    this.includeInfos.add(x);
                }
            });
        }
    }
}
