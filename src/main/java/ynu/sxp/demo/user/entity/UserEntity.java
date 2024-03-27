package ynu.sxp.demo.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ynu.sxp.demo.common.entity.BaseAuditingEntity;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class UserEntity extends BaseAuditingEntity {
    // 用户代码, 用于登录，表示学号、教工号等，唯一
    @Column(length = 20, nullable = false, unique = true)
    private String code;
    // 用户姓名
    @Column(length = 20, nullable = false)
    private String name;
    // 用户密码
    @Column(nullable = false)
    private String password;

    // 用户角色
    @ManyToMany(fetch = FetchType.EAGER) // 立即从数据库中加载
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns=@JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private Set<RoleEntity> roles= new HashSet<>();

}

