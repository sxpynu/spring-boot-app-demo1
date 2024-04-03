package ynu.sxp.demo.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// 登录成功后返回的令牌对象
@Data
@Schema(description = "登录成功后返回的令牌对象")
public class TokenVO {
    // access token
    @Schema(description = "访问令牌")
    public String access_token;
    // refresh token
    @Schema(description = "刷新令牌")
    public String refresh_token;

    @Schema(description = "访问令牌的过期时间")
    public Long expires_in;
}
