package ynu.sxp.demo.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ynu.sxp.demo.common.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

// 这是一个用于记录登录失败信息的实体类
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "login_attempt")
public class LoginAttemptEntity extends BaseEntity {

    // 用户ID
    @Column(nullable = false, unique = true)
    private UUID userId;

    // 登录失败次数
    @Column(nullable = false)
    private Long attemptCount=0L;

    // 最后一次登录失败的时间
    @Column(nullable = false)
    private LocalDateTime lastAttemptTime;

    // 增加登录失败次数
    public void increaseAttemptCount(){
        this.attemptCount++;
    }

}
