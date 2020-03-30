package cn.night.fuo.persistent.jpa.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

//@NoRepositoryBean
@Repository
@EnableJpaRepositories(considerNestedRepositories = false,
        repositoryFactoryBeanClass = FuoRepositoryFactoryBean.class,
//        entityManagerFactoryRef = "entityManagerFactoryMaster",//配置连接工厂 entityManagerFactory
//        transactionManagerRef = "transactionManagerMaster", //配置 事物管理器  transactionManager
        basePackages = "cn.night") //cn.night.fuo.data.repository
public interface FuoRepository<T, ID extends Serializable> {
    @Transactional
    <S extends T> S save(S entity);

    Optional<T> findById(ID id);
}
