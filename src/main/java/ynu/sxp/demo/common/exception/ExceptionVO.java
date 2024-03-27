package ynu.sxp.demo.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "异常信息")
public class ExceptionVO {
    @Schema(description = "异常代码")
    private ExceptionType type;

    @Schema(description = "异常信息")
    private String message;

    @Schema(description = "异常详细信息")
    private String detail;

    public ExceptionVO(ExceptionType type, String message) {
        this.type = type;
        this.message = message;
    }

    public ExceptionVO(ExceptionType type, String message, String detail) {
        this.type = type;
        this.message = message;
        this.detail = detail;
    }
}
