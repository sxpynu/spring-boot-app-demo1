package ynu.sxp.demo.captcha.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ynu.sxp.demo.captcha.vo.CaptchaRO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@EnableScheduling
public class CaptchaService {
    @AllArgsConstructor
    public static class CaptchaData {
        // 验证码ID
        UUID id;
        // 验证码字符串
        String captchaText;
        // 生成时间
        LocalDateTime generateTime;
    }

    Map<UUID, CaptchaData> captchaMap = new HashMap<>();

    public CaptchaDto createCaptcha() throws IOException {
        String captchaText = CaptchaGenerator.generateText(4);
        ByteArrayOutputStream captchaImg = CaptchaGenerator.generateJpegImg(captchaText, 100, 30);

        var captchaId = UUID.randomUUID();
        var captchaData = new CaptchaData(captchaId, captchaText, LocalDateTime.now());

        captchaMap.put(captchaId, captchaData);

        return new CaptchaDto(captchaId, captchaImg);
    }

    public boolean validateCaptcha(CaptchaRO ro) {
        CaptchaData captchaData = captchaMap.get(ro.id);
        if (captchaData != null) {
            captchaMap.remove(ro.id);
            return captchaData.captchaText.equals(ro.captcha);
        }
        return false;
    }

    // 定时清理过期验证码，每隔 5 分钟清理一次
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void cleanExpiredCaptcha() {
        LocalDateTime now = LocalDateTime.now();
        captchaMap.entrySet().removeIf(entry -> entry.getValue().generateTime.plusMinutes(5).isBefore(now));
    }
}

