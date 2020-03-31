package cn.night.project.fuo.test.persistent.entities;

import cn.night.fuo.persistent.common.entites.DomainEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_test_user")
@Data
public class TestEntity extends DomainEntity {

    private String name;

    private Integer age;
}
