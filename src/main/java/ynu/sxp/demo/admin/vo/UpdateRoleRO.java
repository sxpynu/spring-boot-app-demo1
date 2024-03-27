package ynu.sxp.demo.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ynu.sxp.demo.common.util.TrimDeJson;

import java.util.UUID;
@Data
public class UpdateRoleRO {

    @NotNull
    private UUID id;

    @NotEmpty @Length(min = 1, max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    private String code;

    @NotEmpty @Length(min = 1, max = 20)
    @JsonDeserialize(using = TrimDeJson.class)
    private String name;
}
