package cn.night.fuo.data.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class DomainEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
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