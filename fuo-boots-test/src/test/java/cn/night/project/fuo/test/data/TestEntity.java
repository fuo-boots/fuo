package cn.night.project.fuo.test.data;

import cn.night.fuo.data.entities.DomainEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_test_user")
@Data
public class TestEntity extends DomainEntity {

    private String name;

    private Integer age;
}
