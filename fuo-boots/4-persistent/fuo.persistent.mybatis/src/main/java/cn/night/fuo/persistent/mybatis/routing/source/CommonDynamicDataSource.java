package cn.night.fuo.persistent.mybatis.routing.source;

import cn.night.fuo.persistent.mybatis.routing.swicher.DataSourceSwitcherHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CommonDynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSwitcherHolder.getLookupKey();
    }
}
