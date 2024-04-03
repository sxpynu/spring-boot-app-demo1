package ynu.sxp.demo.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;
import ynu.sxp.demo.auth.vo.TokenVO;
import ynu.sxp.demo.user.entity.RoleEntity;
import ynu.sxp.demo.user.entity.UserEntity;
import ynu.sxp.demo.user.service.UserService;

import java.time.Instant;
import java.util.UUID;

/** 用于生成token，包括 access_token 和 refresh_token */
@Service
public class TokenService {

    @Value("${app.login.token-expire-time:3600}")
    private int expireTime;
    private final JwkService jwkService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public TokenService(JwkService JwkService, RefreshTokenService refreshTokenService, UserService userService) {
        this.jwkService = JwkService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    // 生成 Token, 包含：access_token 和 refresh_token
    public TokenVO generateToken(UserEntity user) {
        Instant now = Instant.now(); // 令牌发放时间
        Instant expireAt = now.plusSeconds(expireTime); // 过期时间

        var claims = JwtClaimsSet.builder()
                .issuer("sunxp.ynu.edu.cn")
                .issuedAt(now)
                .expiresAt(expireAt)
                .subject(user.getId().toString())
                .claim("roles", user.getRoles().stream().map(RoleEntity::getCode).toArray())
                .build();

        System.out.printf("role: %s\n", user.getRoles());
        TokenVO tokenVO = new TokenVO();
        tokenVO.access_token = this.jwkService.encodeJwt(claims);
        tokenVO.refresh_token = refreshTokenService.assignRefreshTokenId(user).toString();
        tokenVO.expires_in = expireAt.toEpochMilli();

        return tokenVO;
    }

    // 用先前获得的 refresh_token 换发新的 token

    public TokenVO refreshToken(UUID refreshTokenId) {
        var userId = this.refreshTokenService.getUserId(refreshTokenId);
        UserEntity user = this.userService.getUserById(userId).orElseThrow(() -> new BadCredentialsException("用户已不存在！"));
        return this.generateToken(user);
    }



}

