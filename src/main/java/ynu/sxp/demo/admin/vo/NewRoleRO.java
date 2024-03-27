package ynu.sxp.demo.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ynu.sxp.demo.common.util.TrimDeJson;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewRoleRO {
    @NotEmpty
    @JsonDeserialize(using = TrimDeJson.class)
    private String code;
    @NotEmpty
    @JsonDeserialize(using = TrimDeJson.class)
    private String name;
}

