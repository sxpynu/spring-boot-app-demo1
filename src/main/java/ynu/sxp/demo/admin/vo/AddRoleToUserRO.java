package ynu.sxp.demo.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "为用户添加角色时所需的请求体")
public class AddRoleToUserRO {
    @Schema(description = "用户ID")
    @NotNull
    public UUID userId;
    @Schema(description = "角色ID")
    @NotNull
    public UUID roleId;
}
