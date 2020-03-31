package cn.night.fuo.persistent.common.entites;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class DomainEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modify_time")
    private Date modifyTime;

    private String creator;

    private String modifier;

    @PrePersist
    void createdAt() {
        this.createTime = this.modifyTime = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.modifyTime = new Date();
    }
}