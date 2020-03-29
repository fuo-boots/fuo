package cn.night.fuo.data.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

class BaseRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> {

    private final EntityManager entityManager;

    BaseRepositoryImpl(JpaEntityInformation entityInformation,
                     EntityManager entityManager) {
        super(entityInformation, entityManager);

        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
    }

//    @Transactional
//    public <S extends T> S save(S entity) {
//        entityManager.
//        // implementation goes here
//    }
}

//public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID>  {
//
//    private final EntityManager entityManager;
//    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
//        super(entityInformation, entityManager);
//        this.entityManager = entityManager;
//    }
//
////    @SuppressWarnings("unchecked")
////    @Override
////    public List<Object[]> listBySQL(String sql) {
////        return entityManager.createNativeQuery(sql).getResultList();
////    }
//}
