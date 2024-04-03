package ynu.sxp.demo.captcha.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class CaptchaGenerator {

    // 验证码字符集, 去掉了一些容易混淆的字符, 如 0, O, 1, I
    private static final String CAPTCHA_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final Color[] COLORS = {Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.GRAY};
    public static String generateText(int length) {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CAPTCHA_CHARACTERS.length());
            char character = CAPTCHA_CHARACTERS.charAt(index);
            captcha.append(character);
        }
        return captcha.toString();
    }

    // 用给定的验证码字符串、宽度和高度生成验证码jpg图片，放入一个字节流中返回
    public static ByteArrayOutputStream generateJpegImg(String text, int width, int height) throws IOException {

        Random random = new Random();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        // Fill the background
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // Set font and color
        int fontSize= 20;
        graphics.setFont(new Font("Arial", Font.BOLD, fontSize));

        // Generate random captcha
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            // Set color
            graphics.setColor(COLORS[random.nextInt(COLORS.length)]);
            int angle = random.nextInt(30) - 15; // Random angle between -15 and 15 degrees
            graphics.rotate(Math.toRadians(angle));
            // Draw the character
            graphics.drawString(String.valueOf(character),
                    Math.min(20 * i + random.nextInt(10), width-20),
                    Math.max(Math.min(30 - angle, height), 15));
            graphics.rotate(-Math.toRadians(angle));
        }

        // Add some noise
        for (int i = 0; i < 15; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(10);
            int yl = random.nextInt(10);
            graphics.setColor(COLORS[random.nextInt(COLORS.length)]);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
        graphics.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);
        return byteArrayOutputStream;
    }

}