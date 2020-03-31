package cn.night.fuo.persistent.mybatis_og;

import org.apache.ibatis.session.Configuration;

@FunctionalInterface
public interface ConfigurationCustomizer {
    void customize(Configuration configuration);
}
