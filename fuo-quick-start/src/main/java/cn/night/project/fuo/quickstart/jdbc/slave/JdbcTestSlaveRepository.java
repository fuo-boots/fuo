package cn.night.project.fuo.quickstart.jdbc.slave;

import cn.night.fuo.jdbc.FuoJdbcRepository;
import cn.night.project.fuo.quickstart.TestEntity;


public interface JdbcTestSlaveRepository extends FuoJdbcRepository<TestEntity, Long> {

}