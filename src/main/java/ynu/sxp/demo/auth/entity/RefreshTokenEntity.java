package ynu.sxp.demo.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ynu.sxp.demo.common.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

// 这是一个用于记录刷新令牌的实体类
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity extends BaseEntity {

    // 刷新令牌ID
    @Column(nullable = false, unique = true)
    private UUID refreshTokenId;
    // 用户ID
    @Column(nullable = false, unique = true)
    private UUID userId;

    // 刷新时间
    @Column(nullable = false)
    private LocalDateTime refreshTime;

    // IP地址
    @Column(length = 20, nullable = false)
    private String ip="";

    // 用户代理
    @Column(length = 2000, nullable = false)
    private String userAgent="";
}
