package ynu.sxp.demo.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ynu.sxp.demo.common.util.TrimDeJson;

import java.util.UUID;
public class UpdateRoleRO {

    @NotNull
    public UUID id;

    @Schema(description = "角色代码", maxLength = 20)
    @NotEmpty @Length(min = 1, max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    public String code;

    @Schema(description = "角色名称", maxLength = 20)
    @NotEmpty @Length(min = 1, max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    public String name;
}
