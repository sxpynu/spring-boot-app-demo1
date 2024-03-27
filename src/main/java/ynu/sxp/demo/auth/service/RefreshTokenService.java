package ynu.sxp.demo.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ynu.sxp.demo.auth.entity.RefreshTokenEntity;
import ynu.sxp.demo.auth.repository.IRefreshTokenRepository;
import ynu.sxp.demo.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final IRefreshTokenRepository refreshTokenRepository;

    RefreshTokenService(IRefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // 为给出的用户分配一个刷新令牌Id
    public UUID assignRefreshTokenId(UserEntity user) {
        var refreshTokenEntity =  refreshTokenRepository.findByUserId(user.getId())
                .orElseGet(() -> this.createRefreshToken(user));
        updateRefreshToken(refreshTokenEntity);
        return refreshTokenEntity.getRefreshTokenId();
    }

    // 用给出的刷新令牌Id 获得拥有此令牌的用户Id
    public UUID getUserId(UUID refreshTokenId) {
        var refreshTokenEntity = refreshTokenRepository.findByRefreshTokenId(refreshTokenId)
                .orElseThrow(() -> new BadCredentialsException("无效的刷新令牌！"));
        return refreshTokenEntity.getUserId();
    }

    // 为给出的用户创建一个新的 RefreshTokenEntity 对象
    private RefreshTokenEntity createRefreshToken(UserEntity user) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUserId(user.getId());
        return refreshTokenEntity;
    }

    // 更新并保存 RefreshTokenEntity 对象
    private void updateRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        // 获取当前请求对象
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();
        refreshTokenEntity.setRefreshTokenId(UUID.randomUUID());
        refreshTokenEntity.setRefreshTime(LocalDateTime.now());
        refreshTokenEntity.setIp(request.getRemoteAddr());
        String userAgent = request.getHeader("user-agent");
        userAgent = userAgent == null ? "unknown" : userAgent.substring(0, Math.min(userAgent.length(), 2000));
        refreshTokenEntity.setUserAgent(userAgent);
        refreshTokenRepository.save(refreshTokenEntity);
    }


}
