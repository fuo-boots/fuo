package cn.night.fuo.persistent.common;

import org.springframework.context.annotation.Import;

@Import(cn.night.fuo.persistent.common.datasource.DataSourceConfig.class)
public class PersistentStarter {

    public PersistentStarter(){

    }
}
