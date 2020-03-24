package cn.night.fuo.web.mvc;

import cn.night.fuo.core.conf.IConf;
import cn.night.fuo.pattern.asserts.Assert;
import cn.night.fuo.spring.SpringContextHolder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InterceptorConf implements IConf {
    private String name;
    private String interceptor;
    private List<String> path = new ArrayList<>();
    private List<String> excludes = new ArrayList<>();


    @Override
    public void initializing()  {
        if(StringUtils.isEmpty(this.name)){
            this.name = this.interceptor;
        }
    }

    @Override
    public void inspecting() {
        Assert.notNull(this.interceptor,"web.mvc.interceptors.interceptor can not be null");
        Assert.notNull(this.name,"web.mvc.interceptors.name can not be null");

        Assert.notNull(SpringContextHolder.getBeanByClazz(this.interceptor),"web.mvc.interceptors.interceptor can not load");
        Assert.collectionNotEmpty(this.path,"web.mvc.interceptors.path can not be empty");
        Assert.notNull(this.excludes,"web.mvc.interceptors.excludes can not be null");
    }
}
