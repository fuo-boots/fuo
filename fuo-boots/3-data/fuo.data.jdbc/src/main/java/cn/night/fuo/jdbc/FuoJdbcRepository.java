package cn.night.fuo.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FuoJdbcRepository<T,ID> extends JpaRepository<T,ID> {

}
