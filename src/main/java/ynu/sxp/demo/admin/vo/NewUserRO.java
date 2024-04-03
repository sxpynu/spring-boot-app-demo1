package ynu.sxp.demo.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ynu.sxp.demo.common.util.TrimDeJson;


@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增用户所需的请求体对象")
public class NewUserRO {
    @Schema(description = "用户代码", maxLength = 20)
    @NotEmpty @Length(max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    public String code;
    @Schema(description = "用户姓名", maxLength = 20)
    @NotEmpty @Length(max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    public String name;
    @Schema(description = "用户密码")
    @NotEmpty
    public String password;

}

