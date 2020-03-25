package cn.night.fuo.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FuoJdbcRepository<T,ID> extends JpaRepository<T,ID> {

}
