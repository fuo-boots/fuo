package cn.night.fuo.web.log;

import cn.night.fuo.core.conf.ConfConstants;
import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "fuo.web.log", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Data
public class WebLogConf implements IConf {

    public class WebLogIncludeInfoName {
        public static final String REQUEST_QUERYSTRING = "REQUEST_QUERYSTRING";
        public static final String REQUEST_IP = "REQUEST_IP";
        public static final String REQUEST_METHOD = "REQUEST_METHOD";
        public static final String REQUEST_URI = "REQUEST_URI";
        public static final String REQUEST_BODY = "REQUEST_BODY";
        public static final String REQUEST_HEADER = "REQUEST_HEADER";
        public static final String REQUEST_COOKIE = "REQUEST_COOKIE";
        public static final String RESPONSE_BODY = "RESPONSE_BODY";
        public static final String EXCEPTION = "EXCEPTION";
    }

    private Boolean enabled = true;
    private List<String> methodFilters = new ArrayList<>();
    private List<String> includeInfos = new ArrayList<>();


    @Override
    public void initializing(){
        if (methodFilters.isEmpty()) {
            methodFilters.add(ConfConstants.ANY);
        }
        if (includeInfos.isEmpty()) {
            includeInfos.addAll(Arrays.asList(WebLogIncludeInfoName.REQUEST_URI,
                    WebLogIncludeInfoName.REQUEST_QUERYSTRING, WebLogIncludeInfoName.REQUEST_BODY,WebLogIncludeInfoName.REQUEST_METHOD,
                    WebLogIncludeInfoName.RESPONSE_BODY, WebLogIncludeInfoName.EXCEPTION));
        }
    }

    @Override
    public void inspecting(){
        Assert.notNull(this.enabled, "fuo.web.log.enabled can not be null");
        Assert.notNull(this.methodFilters, "fuo.web.log.methodFilters can not be null");
        Assert.notNull(this.includeInfos, "fuo.web.log.includeInfos can not be null");
        if(this.enabled) {
            Assert.collectionNotEmpty(this.methodFilters, "config:fuo.web.log.enabled = true, so fuo.web.log.methodFilters can not be empty");
            Assert.collectionNotEmpty(this.includeInfos, "config:fuo.web.log.enabled = true, so fuo.web.log.includeInfos can not be empty");
        }
    }
}
