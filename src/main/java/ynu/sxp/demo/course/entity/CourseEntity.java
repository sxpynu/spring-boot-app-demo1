package ynu.sxp.demo.course.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ynu.sxp.demo.common.entity.BaseAuditingEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "course")
public class CourseEntity extends BaseAuditingEntity {
    @Column(length = 20, nullable = false)
    private String code;

    @Column(length = 50, nullable = false)
    private String name;
}