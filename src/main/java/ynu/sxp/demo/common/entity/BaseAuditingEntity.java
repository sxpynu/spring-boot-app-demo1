package ynu.sxp.demo.common.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

// 这是一个用于让Spring Data JPA自动填充审计字段的基类，
// 包含了创建时间、更新时间、创建者和更新者，用于记录实体的创建和更新信息
// 通过继承这个类，可以让实体类自动填充这些字段
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseAuditingEntity extends BaseEntity {
    @CreatedDate
    LocalDateTime createdDate;

    @LastModifiedDate
    LocalDateTime updatedDate;

    @CreatedBy
    UUID createdBy;

    @LastModifiedBy
    UUID updatedBy;
}
