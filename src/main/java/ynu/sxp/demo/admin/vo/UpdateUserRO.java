package ynu.sxp.demo.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ynu.sxp.demo.common.util.TrimDeJson;

import java.util.UUID;
@Data
@Schema(description = "更新用户基本信息的请求体对象")
public class UpdateUserRO {
    @NotNull
    private UUID id;
    @NotEmpty @Length(max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    private String code;
    @NotEmpty @Length(max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    private String name;
}
