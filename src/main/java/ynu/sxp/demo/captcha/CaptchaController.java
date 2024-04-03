package ynu.sxp.demo.captcha;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ynu.sxp.demo.captcha.service.CaptchaDto;
import ynu.sxp.demo.captcha.service.CaptchaService;
import ynu.sxp.demo.captcha.vo.CaptchaRO;
import ynu.sxp.demo.captcha.vo.CaptchaVO;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
@Tag(name = "captcha", description = "验证码相关接口")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Operation(summary = "获取验证码图片", description = "获取验证码图片")
    @GetMapping(value = "/captcha/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getCaptchaImage(HttpServletResponse response) throws IOException {
        var captchaDto = captchaService.createCaptcha();
        captchaDto.image.writeTo(response.getOutputStream());
    }

    // 返回 CaptchaVO 的 API
    @Operation(summary = "获取验证码", description = "获取验证码")
    @GetMapping("/captcha/base64")
    public CaptchaVO getCaptcha() throws IOException {
        CaptchaDto captcha = captchaService.createCaptcha();
        String imgBase64 = Base64.getEncoder().encodeToString(captcha.image.toByteArray());
        return new CaptchaVO(captcha.id, imgBase64);
    }

    @Operation(summary = "验证验证码", description = "验证用户输入的验证码是否正确")
    @PostMapping("/validate-captcha")
    public boolean validateCaptcha(@RequestBody CaptchaRO Ro) {
        return captchaService.validateCaptcha(Ro);
    }
}