package ynu.sxp.demo.captcha.service;

import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@AllArgsConstructor
public class CaptchaDto {
    // 验证码Id
    public UUID id;
    // 验证码图片字节流
    public ByteArrayOutputStream image;
}
