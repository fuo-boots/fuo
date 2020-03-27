package cn.night.project.fuo.quickstart.jdbc.main;

import cn.night.fuo.jdbc.FuoJdbcRepository;
import cn.night.project.fuo.quickstart.TestEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

public interface JdbcTestMainRepository extends FuoJdbcRepository<TestEntity, Long> {

}
