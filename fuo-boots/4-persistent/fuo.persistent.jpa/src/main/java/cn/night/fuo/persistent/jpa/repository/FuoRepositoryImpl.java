package cn.night.fuo.persistent.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class FuoRepositoryImpl <T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements FuoRepository<T,ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation entityInformation;

    public FuoRepositoryImpl(JpaEntityInformation entityInformation,EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }
}
