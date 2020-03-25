package cn.night.fuo.data.mongdb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FuoMongoRepository<T,ID> extends MongoRepository<T,ID> {

}
