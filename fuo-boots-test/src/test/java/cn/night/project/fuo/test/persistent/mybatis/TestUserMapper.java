package cn.night.project.fuo.test.persistent.mybatis;

import cn.night.project.fuo.test.persistent.entities.TestEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestUserMapper {
    @Insert("select id, name, state, country from city where state = #{state}")
    void save(TestEntity testEntity);

    @Select("select * from city where id = #{id}")
    void findById(@Param("id")Long id);
}
