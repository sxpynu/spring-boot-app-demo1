package ynu.sxp.demo.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ynu.sxp.demo.common.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "Role")
public class RoleEntity extends BaseEntity {
    // 角色代码
    @Column(length = 20, nullable = false, unique = true)
    private String code;
    // 角色名称
    @Column(length = 20, nullable = false)
    private String name;

}
