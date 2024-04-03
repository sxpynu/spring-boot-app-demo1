package ynu.sxp.demo.auth.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import ynu.sxp.demo.common.util.TrimDeJson;


@Schema(description = "登录时提交的请求体")
public class LoginRO {
    @Schema(description = "用户登录名", example = "admin")
    @NotEmpty
    @JsonDeserialize(using = TrimDeJson.class)
    public String username;
    @Schema(description = "登录密码", example = "123456")
    @NotEmpty
    public String password;
}

