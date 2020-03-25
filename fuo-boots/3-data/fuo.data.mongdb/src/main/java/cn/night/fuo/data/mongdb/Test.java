package cn.night.fuo.data.mongdb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Test extends MongoRepository<TClass, TClass> {
}

class TClass {

}
